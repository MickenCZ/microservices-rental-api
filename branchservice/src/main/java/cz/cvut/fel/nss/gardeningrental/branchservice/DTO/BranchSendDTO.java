package cz.cvut.fel.nss.gardeningrental.branchservice.DTO;

import cz.cvut.fel.nss.gardeningrental.branchservice.model.Address;
import cz.cvut.fel.nss.gardeningrental.branchservice.model.Branch;

import java.util.List;

public class BranchSendDTO {
    private final Long id;
    private final String name;

    private final Address address;

    private final List<ItemSendDTO> items;

    public BranchSendDTO(Branch branch) {
        this.id = branch.getId();
        this.name = branch.getName();
        this.address = branch.getAddress();
        this.items = branch.getItems().stream().map(item -> new ItemSendDTO(item)).toList();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<ItemSendDTO> getItems() {
        return items;
    }
}
