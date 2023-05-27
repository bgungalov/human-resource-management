package com.usm.UserManagmentService.Entity;

/**
 * Separate the construction of a complex object
 * from its representation so that the same construction
 * process can create different representations.
 */
public class UserDetailsBuilder {
    private int id;
    private String ucn;
    private String address;
    private String country;
    private String city;
    private String cityPostCode;
    private String street;

    public UserDetailsBuilder(int id, String ucn, String address, String country,
                              String city, String cityPostCode, String street) {
        this.id = id;
        this.ucn = ucn;
        this.address = address;
        this.country = country;
        this.city = city;
        this.cityPostCode = cityPostCode;
        this.street = street;
    }

    public UserDetailsBuilder() {
    }

    public UserDetails build() {
        return new UserDetails(this.id, this.ucn, this.address, this.country, this.city, this.cityPostCode, this.street);
    }

    public UserDetailsBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserDetailsBuilder setUcn(String ucn) {
        this.ucn = ucn;
        return this;
    }

    public UserDetailsBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserDetailsBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public UserDetailsBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public UserDetailsBuilder setCityPostCode(String cityPostCode) {
        this.cityPostCode = cityPostCode;
        return this;
    }

    public UserDetailsBuilder setStreet(String street) {
        this.street = street;
        return this;
    }


}
