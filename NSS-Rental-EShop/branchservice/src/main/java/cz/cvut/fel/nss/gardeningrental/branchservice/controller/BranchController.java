package cz.cvut.fel.nss.gardeningrental.branchservice.controller;

import cz.cvut.fel.nss.gardeningrental.branchservice.DTO.BranchCreateDTO;
import cz.cvut.fel.nss.gardeningrental.branchservice.DTO.BranchSendDTO;
import cz.cvut.fel.nss.gardeningrental.branchservice.model.Branch;
import cz.cvut.fel.nss.gardeningrental.branchservice.service.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/")
    public ResponseEntity<List<BranchSendDTO>> getAllBranches() {
        return new ResponseEntity<>(branchService
                .getAllBranches()
                .stream()
                .map(branch -> new BranchSendDTO(branch))
                .toList(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createBranch(@RequestBody BranchCreateDTO branchCreateDTO) {
        Branch branch = branchService.createBranch(branchCreateDTO.getName(), branchCreateDTO.getCountry(), branchCreateDTO.getCity(), branchCreateDTO.getStreet(), branchCreateDTO.getStreetNumber(), branchCreateDTO.getPostalCode());
        return new ResponseEntity<>(new BranchSendDTO(branch), HttpStatus.CREATED);
    }
}
