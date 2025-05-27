package cz.cvut.fel.nss.gardeningrental.branchservice.DTO;

import cz.cvut.fel.nss.gardeningrental.branchservice.model.Item;

public class ItemSendDTO {
    private final Long productId;
    private final Long branchid;
    private final String serialNumber;

    public ItemSendDTO(Long productId, Long branchid, String serialNumber) {
        this.productId = productId;
        this.branchid = branchid;
        this.serialNumber = serialNumber;
    }

    public ItemSendDTO(Item item) {
        this.productId = item.getProductId();
        this.serialNumber = item.getSerialNumber();
        this.branchid = item.getBranch().getId();
    }

    public Long getProductId() {
        return productId;
    }

    public Long getBranchid() {
        return branchid;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
