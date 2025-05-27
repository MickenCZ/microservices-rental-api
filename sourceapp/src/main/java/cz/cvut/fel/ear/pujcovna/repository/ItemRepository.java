package cz.cvut.fel.ear.pujcovna.repository;

import cz.cvut.fel.ear.pujcovna.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item getItemBySerialNumber(@Param("serialNumber") String serialNumber);
    void deleteItemBySerialNumber(@Param("serialNumber") String serialNumber);
}
