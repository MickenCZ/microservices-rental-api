package cz.cvut.fel.nss.gardeningrental.branchservice.controller;

import cz.cvut.fel.nss.gardeningrental.branchservice.DTO.ItemCreationDTO;
import cz.cvut.fel.nss.gardeningrental.branchservice.DTO.ItemSendDTO;
import cz.cvut.fel.nss.gardeningrental.branchservice.model.Item;
import cz.cvut.fel.nss.gardeningrental.branchservice.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addItemToStock(@RequestBody ItemCreationDTO itemDTO) {
        itemService.addItemToStock(itemDTO.getSerialNumber(), itemDTO.getProductId(), itemDTO.getBranchId());
        return new ResponseEntity<>("Item of product id " + itemDTO.getProductId() + " added to stock of branch: " + itemDTO.getBranchId(), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> getItemsInStock(@RequestParam Long productId, @RequestParam Long branchId) {
        List<Item> items = itemService.getItemsInStock(productId, branchId);
        List<ItemSendDTO> itemsDTOs = items.stream().map(item -> new ItemSendDTO(item)).toList();
        return new ResponseEntity<>(itemsDTOs, HttpStatus.OK);
    }
}
