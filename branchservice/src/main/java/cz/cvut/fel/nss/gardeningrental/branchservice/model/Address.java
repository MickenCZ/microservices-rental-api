package cz.cvut.fel.nss.gardeningrental.branchservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String streetNumber;
    @Column(nullable = false)
    private int postalCode;

    public Address() {
    }

    public Address(String country, String city, String street, String streetNumber, int postalCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
