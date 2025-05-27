package cz.cvut.fel.ear.pujcovna.controller;

import cz.cvut.fel.ear.pujcovna.dto.ItemCreationDTO;
import cz.cvut.fel.ear.pujcovna.dto.LoanIdDTO;
import cz.cvut.fel.ear.pujcovna.dto.SerialNumberDTO;
import cz.cvut.fel.ear.pujcovna.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage")
public class StorageController {
    private final ItemService itemService;
    public StorageController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addProductToStorage(@Valid @RequestBody ItemCreationDTO itemDTO) {
        itemService.addItemToStock(itemDTO.getSerialNumber(), itemDTO.getProductName());
        return new ResponseEntity<>("Item of product " + itemDTO.getProductName() + " added to stock.", HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> removeProductFromStorage(@Valid @RequestBody SerialNumberDTO serialNumberDTO) {
        itemService.removeItemFromStock(serialNumberDTO.getSerialNumber());
        return new ResponseEntity<>("Item " + serialNumberDTO.getSerialNumber() + " removed from stock.", HttpStatus.OK);
    }

    @PutMapping("/return")
    public ResponseEntity<?> returnItemsToStock(@Valid @RequestBody LoanIdDTO loanIdDTO) {
        itemService.returnItemsToStock(loanIdDTO.getLoanID());
        return new ResponseEntity<>("All items of loan " + loanIdDTO.getLoanID() + " have been returned to stock.", HttpStatus.OK);
    }
}
