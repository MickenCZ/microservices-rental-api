package cz.cvut.fel.nss.gardeningrental.branchservice.repository;

import cz.cvut.fel.nss.gardeningrental.branchservice.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findBranchByName(String name);
}
