package cz.cvut.fel.nss.gardeningrental.branchservice.repository;

import cz.cvut.fel.nss.gardeningrental.branchservice.model.Branch;
import cz.cvut.fel.nss.gardeningrental.branchservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findBySerialNumber(String serialNumber);
    List<Item> findAllByProductId(Long productId);

    int countByProductIdAndBranch(Long productId, Branch branch);

    List<Item> findAllByProductIdAndBranch(Long productId, Branch branch);
}
