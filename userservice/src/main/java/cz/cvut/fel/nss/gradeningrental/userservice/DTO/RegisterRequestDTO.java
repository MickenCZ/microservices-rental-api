package cz.cvut.fel.nss.gradeningrental.userservice.DTO;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequestDTO {
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Fullname cannot be empty")
    private String fullName;
    @NotBlank(message = "Phone Number cannot be empty")
    private String phoneNumber;

    // address fields
    @NotBlank(message = "Country cannot be empty")
    private String country;
    @NotBlank(message = "City cannot be empty")
    private String city;
    @NotBlank(message = "Street cannot be empty")
    private String street;
    @NotBlank(message = "Street Number cannot be empty")
    private String streetNumber;
    @NotBlank(message = "Postal Code cannot be empty")
    private String postalCode;

    public RegisterRequestDTO() {
    }

    public @NotBlank(message = "Country cannot be empty") String getCountry() {
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
