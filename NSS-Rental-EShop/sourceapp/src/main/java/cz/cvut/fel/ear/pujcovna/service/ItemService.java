package cz.cvut.fel.ear.pujcovna.service;

import cz.cvut.fel.ear.pujcovna.exception.EntityNotFoundException;
import cz.cvut.fel.ear.pujcovna.exception.RemovedBorrowedItemException;
import cz.cvut.fel.ear.pujcovna.model.Item;
import cz.cvut.fel.ear.pujcovna.model.Loan;
import cz.cvut.fel.ear.pujcovna.model.Product;
import cz.cvut.fel.ear.pujcovna.repository.ItemRepository;
import cz.cvut.fel.ear.pujcovna.repository.LoanRepository;
import cz.cvut.fel.ear.pujcovna.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final LoanRepository loanRepository;

    public ItemService(ProductRepository productRepository, ItemRepository itemRepository, LoanRepository loanRepository) {
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addItemToStock(String serialNumber, String productName) {
        Product product = productRepository.findProductByName(productName);
        if (product != null) {
            Item item = new Item(serialNumber, product);
            itemRepository.save(item);
            product.getItems().add(item);
            productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product with id " + productName + " not found.");
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeItemFromStock(String serialNumber) {
        Item item = itemRepository.getItemBySerialNumber(serialNumber);
        if (item == null) {
            throw new EntityNotFoundException("Item " + serialNumber + " not found.");
        }
        if (item.getLoan() != null) {
            throw new RemovedBorrowedItemException("Cannot remove item " + serialNumber + ", because borrowed it and still has it.");
        }
        itemRepository.deleteItemBySerialNumber(serialNumber);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void returnItemsToStock(Long loanId) {
        // All items have their tie to the loan removed (so they can be used for other loans),
        // and Loan is marked as returned (not deleted, for account reasons)
        Loan loan = loanRepository.getReferenceById(loanId);
        loan.setReturned(true);
        for (Item borrowedItem : loan.getItems()) {
            borrowedItem.setLoan(null);
            itemRepository.save(borrowedItem);
        }
        loanRepository.save(loan);
    }
}
