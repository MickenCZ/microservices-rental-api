package cz.cvut.fel.nss.gradeningrental.userservice.repository;

import cz.cvut.fel.nss.gradeningrental.userservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
