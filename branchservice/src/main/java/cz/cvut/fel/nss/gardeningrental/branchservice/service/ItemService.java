package cz.cvut.fel.nss.gardeningrental.branchservice.service;

import cz.cvut.fel.nss.gardeningrental.branchservice.exception.EntityNotFoundException;
import cz.cvut.fel.nss.gardeningrental.branchservice.model.Branch;
import cz.cvut.fel.nss.gardeningrental.branchservice.model.Item;
import cz.cvut.fel.nss.gardeningrental.branchservice.repository.BranchRepository;
import cz.cvut.fel.nss.gardeningrental.branchservice.repository.ItemRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final BranchRepository branchRepository;
    private final KafkaProducer kafkaProducer;
    private final RestTemplate restTemplate;

    public ItemService(ItemRepository itemRepository, BranchRepository branchRepository, KafkaProducer kafkaProducer, RestTemplate restTemplate) {
        this.itemRepository = itemRepository;
        this.branchRepository = branchRepository;
        this.kafkaProducer = kafkaProducer;
        this.restTemplate = restTemplate;
    }

    private boolean doesProductExist(Long productId) {
        String endpointURL = String.format("http://productservice:8081/products/exists/%d", productId);
        return restTemplate.getForObject(endpointURL, Boolean.class);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public void addItemToStock(String serialNumber, Long productId, Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch with id: " + branchId + " not found."));

        // Calling product service to verify product with that id is real
        boolean productExists = doesProductExist(productId);
        if (!productExists) {
            throw new EntityNotFoundException("Product with id " + productId + " not found.");
        }
        Item item = new Item(serialNumber, branch, productId);
        kafkaProducer.sendMessage("New item with serial number", serialNumber, "added to stock on branch", branchId.toString());
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public List<Item> getItemsInStock(Long productId, Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch with id: " + branchId + " not found."));

        return itemRepository.findAllByProductIdAndBranch(productId, branch);
    }
}
