package cz.cvut.fel.ear.pujcovna.service;

import cz.cvut.fel.ear.pujcovna.exception.EntityNotFoundException;
import cz.cvut.fel.ear.pujcovna.exception.ForbiddenDateException;
import cz.cvut.fel.ear.pujcovna.exception.NotEnoughStockException;
import cz.cvut.fel.ear.pujcovna.model.Item;
import cz.cvut.fel.ear.pujcovna.model.Loan;
import cz.cvut.fel.ear.pujcovna.model.User;
import cz.cvut.fel.ear.pujcovna.repository.ItemRepository;
import cz.cvut.fel.ear.pujcovna.repository.LoanRepository;
import cz.cvut.fel.ear.pujcovna.repository.ProductRepository;
import cz.cvut.fel.ear.pujcovna.repository.UserRepository;
import cz.cvut.fel.ear.pujcovna.service.security.SecurityUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, ProductRepository productRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public Loan create(LocalDate fromDate, LocalDate toDate, List<String> productNames) {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedInUsername());


        validateDates(fromDate, toDate);

        List<Item> warehouseItemsToBorrow = findItemsToBorrow(productNames);
        BigDecimal price = calculatePriceOfItems(warehouseItemsToBorrow, fromDate, toDate);

        Loan finalLoan = new Loan(fromDate, toDate, price, false, warehouseItemsToBorrow, user);

        // Set the loan on item, so we know who borrowed it.
        for (Item item : finalLoan.getItems()) {
            item.setLoan(finalLoan);
            itemRepository.save(item);
        }

        loanRepository.save(finalLoan);
        return finalLoan;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Loan> getAllLoansByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return loanRepository.getAllByUsername(username);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Loan> getAllLoansTotal() {
        return loanRepository.findAll();
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BigDecimal getRevenueBetweenDates(LocalDate startDate, LocalDate endDate) {
        return loanRepository.getTotalMoneyPaidBetween(startDate, endDate);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Object[]> getTopNBorrowedProducts(LocalDate startDate, LocalDate endDate, int N) {
        Pageable topN = PageRequest.of(0, N);
        return loanRepository.getTopBorrowedProducts(startDate, endDate, topN);
    }

    private long calculateDaysCharged(LocalDate odDatum, LocalDate doDatum) {
        long daysBetween = Math.abs(ChronoUnit.DAYS.between(odDatum, doDatum)); // Calculate the days between
        if (daysBetween == 0) {
            return 1; // Can't charge for less than 1 day
        } else {
            return daysBetween;
        }
    }

    private void validateDates(LocalDate fromDate, LocalDate toDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedOdDatum = fromDate.format(dateFormatter);
        String formattedDoDatum = toDate.format(dateFormatter);
        if (toDate.isBefore(fromDate)) { // Can't borrow items for a negative amount of time
            throw new ForbiddenDateException(String.format("Time between fromdate %s and toDate %s is negative.", formattedOdDatum, formattedDoDatum));
        }
        if (fromDate.isBefore(LocalDate.now())) { // Can't borrow items in the past
            throw new ForbiddenDateException(String.format("FromDate %s is invalid, because you can't borrow items in the past.", formattedOdDatum));
        }
        if (fromDate.isAfter(LocalDate.now().plusYears(1))) { // Can't borrow items more than a year ahead
            throw new ForbiddenDateException(String.format("FromDate %s is invalid, because you can't borrow items more than a year ahead.", formattedOdDatum));
        }
    }

    private List<Item> findItemsToBorrow(List<String> productNames) {
        // Find a list of invalid ids and give them to the user
        try {
            List<Long> productIds = productNames.stream().map(name -> productRepository.findProductByName(name).getId()).toList();
            Map<Long, Integer> productCount = new HashMap<>();
            for (Long productId : productIds) {
                productCount.put(productId, productCount.getOrDefault(productId, 0) + 1);
            } // Key: ProductID, value: ordered count

            // Finds specific items to borrow and stores them in this list
            List<Item> borrowedItems = new ArrayList<>();
            for (Map.Entry<Long, Integer> entry : productCount.entrySet()) { // For every product in map
                for (int i = 0; i < entry.getValue(); i++) { // If orders product 5 times, repeat 5 times
                    Random random = new Random(); // We want to select a random item from stock
                    List<Item> availableItems = productRepository.findAvailableItems(entry.getKey());
                    if (availableItems.isEmpty()) {
                        throw new NotEnoughStockException(String.format("Product with id %d doesn't have enough stock.", entry.getKey()));
                    }
                    int randomIndex = random.nextInt(availableItems.size());
                    Item selectedItem = availableItems.remove(randomIndex);
                    borrowedItems.add(selectedItem);
                    // add random available item to borrowed
                }
            }
            return borrowedItems;
        } catch (NullPointerException e) {
            throw new EntityNotFoundException("Products with these names have not been found.");
        }
    }

    private BigDecimal calculatePriceOfItems(List<Item> items, LocalDate fromDate, LocalDate toDate) {
        BigDecimal price = new BigDecimal(0); // Calculate total price
        for (Item item : items) {
            // We either charge the amount of days, or if it's less than one day, we charge one day
            long daysCharged = calculateDaysCharged(fromDate, toDate);
            BigDecimal dayCost = item.getProduct().getDailyPrice();
            BigDecimal itemCost = dayCost.multiply(BigDecimal.valueOf(daysCharged));
            price = price.add(itemCost);
        }
        return price;
    }
}
