package cz.cvut.fel.ear.pujcovna.service;

import cz.cvut.fel.ear.pujcovna.exception.EntityNotFoundException;
import cz.cvut.fel.ear.pujcovna.exception.ForbiddenDateException;
import cz.cvut.fel.ear.pujcovna.exception.NotEnoughStockException;
import cz.cvut.fel.ear.pujcovna.model.Product;
import cz.cvut.fel.ear.pujcovna.model.Item;
import cz.cvut.fel.ear.pujcovna.model.Loan;
import cz.cvut.fel.ear.pujcovna.repository.ItemRepository;
import cz.cvut.fel.ear.pujcovna.repository.LoanRepository;
import cz.cvut.fel.ear.pujcovna.repository.ProductRepository;
import cz.cvut.fel.ear.pujcovna.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:application-test.properties")
@Profile("test")
public class LoanServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private LoanService loanService;

    private List<String> productNames;

    private LocalDate fromDateTesting;
    private LocalDate toDateTesting;



    @BeforeEach
    public void setUp() {
        // so I don't have to specify it each time
        productNames = Arrays.asList("Vybaveni1", "Vybaveni2");

        LocalDateTime now = LocalDateTime.now();
        fromDateTesting = now.plusHours(13).toLocalDate();
        toDateTesting = now.plusHours(13 + 24).toLocalDate();

        setUpSecurityContext();
    }

    private void setUpSecurityContext() {
        UserDetails userDetails = User.withUsername("testUser")
                .password("password")
                .roles("USER")
                .build();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Test
    public void createThrowsForbiddenDateExceptionForNegativeTimePeriod() {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = fromDate.minusDays(1);

        assertThrows(ForbiddenDateException.class, () -> {
            loanService.create(fromDate, toDate, productNames);
        });
    }

    @Test
    public void createThrowsForbiddenDateExceptionForPastDate() {
        LocalDate fromDate = LocalDate.now().minusDays(1);
        LocalDate toDate = LocalDate.now();

        assertThrows(ForbiddenDateException.class, () -> {
            loanService.create(fromDate, toDate, productNames);
        });
    }

    @Test
    public void createThrowsForbiddenDateExceptionForMoreThanAYearAhead() {
        LocalDate fromDate = LocalDate.now().plusYears(2);
        LocalDate toDate = fromDate.plusDays(1);

        assertThrows(ForbiddenDateException.class, () -> {
            loanService.create(fromDate, toDate, productNames);
        });
    }

    @Test
    public void createThrowsEntityNotFoundExceptionForInvalidNames() {
        when(productRepository.findInvalidProductIds(anyList()))
                .thenReturn(Collections.singletonList(10L));
        assertThrows(EntityNotFoundException.class, () -> {
            loanService.create(fromDateTesting, toDateTesting, productNames);
        });
    }

    @Test
    public void createThrowsNotEnoughStockExceptionForNotEnoughStock() {
        // Assuming every id is valid (handled by last test)
        when(productRepository.findInvalidProductIds(anyList())).thenReturn(Collections.emptyList());
        when(productRepository.findAvailableItems(anyLong())).thenReturn(Collections.emptyList());
        when(productRepository.findProductByName(anyString())).thenReturn(new Product());

        assertThrows(NotEnoughStockException.class, () -> {
            loanService.create(fromDateTesting, toDateTesting, productNames);
        });

        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    public void successfullyCreatedLoan() {
        // Create the products and items
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName(productNames.get(0));
        product1.setDailyPrice(new BigDecimal("100.00"));

        Product product2 = new Product();
        product1.setName(productNames.get(1));
        product2.setId(2L);
        product2.setDailyPrice(new BigDecimal("200.00"));

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Item item1 = new Item();
        item1.setId(1L);
        item1.setProduct(product1);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setProduct(product2);



        List<Item> availableItems1 = new ArrayList<>();
        availableItems1.add(item1);

        List<Item> availableItems2 = new ArrayList<>();
        availableItems2.add(item2);

        when(productRepository.findAvailableItems(1L)).thenReturn(availableItems1);
        when(productRepository.findAvailableItems(2L)).thenReturn(availableItems2);
        when(productRepository.findProductByName(productNames.get(0))).thenReturn(product1);
        when(productRepository.findProductByName(productNames.get(1))).thenReturn(product2);

        Loan loan = loanService.create(fromDateTesting, toDateTesting, productNames);

        // Check if we verified the availability of the items
        verify(productRepository, times(2)).findAvailableItems(any(Long.class));

        BigDecimal expectedPrice = new BigDecimal(300); // 100 for one day of item 1 + 200 for one day of item 2
        assert (loan.getPrice().compareTo(expectedPrice) == 0);

        // Verify each item now belongs to the just created loan
        for (Item item : loan.getItems()) {
            assert (item.getLoan().equals(loan));
        }

        // Verify Loan has the correct Items
        assert (loan.getItems().contains(item1));
        assert (loan.getItems().contains(item2));
    }


}
