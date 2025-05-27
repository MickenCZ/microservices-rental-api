package cz.cvut.fel.nss.gardeningrental.branchservice.DTO;

public class BranchCreateDTO {
    private final String name;

    private final String country;
    private final String city;

    private final String street;

    private final String streetNumber;

    private final int postalCode;

    public BranchCreateDTO(String name, String country, String city, String street, String streetNumber, int postalCode) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }
}
