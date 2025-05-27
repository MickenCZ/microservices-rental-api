package cz.cvut.fel.nss.gardeningrental.branchservice.DTO;

public class ItemCreationDTO {
    private final Long productId;
    private final Long branchId;
    private final String serialNumber;

    public ItemCreationDTO(Long productId, Long branchId, String serialNumber) {
        this.productId = productId;
        this.branchId = branchId;
        this.serialNumber = serialNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
