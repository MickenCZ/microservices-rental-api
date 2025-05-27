package cz.cvut.fel.nss.gardeningrental.branchservice.service;

import cz.cvut.fel.nss.gardeningrental.branchservice.model.Address;
import cz.cvut.fel.nss.gardeningrental.branchservice.model.Branch;
import cz.cvut.fel.nss.gardeningrental.branchservice.repository.BranchRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchService {
    private final BranchRepository branchRepository;

    private final KafkaProducer kafkaProducer;

    public BranchService(BranchRepository branchRepository, KafkaProducer kafkaProducer) {
        this.branchRepository = branchRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Branch createBranch(String branchName, String country, String city, String street, String streetNumber, int postalCode) {
        Branch branch = new Branch(branchName, new Address(country, city, street, streetNumber, postalCode));
        kafkaProducer.sendMessage("New branch created", branchName);
        return branchRepository.save(branch);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }
}
