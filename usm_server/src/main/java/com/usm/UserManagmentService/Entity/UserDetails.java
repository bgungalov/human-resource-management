package com.usm.UserManagmentService.Entity;

import java.io.Serializable;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * This is a model class for user details.
 */
@Entity
@Table(name = "userDetails")
public class UserDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank(message = "Field may not be blank")
    @Pattern(regexp = "^\\d{10}")
    String ucn;

    String address;
    String country;
    String city;
    String cityPostCode;
    String street;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "id")
    User user;

    public UserDetails() {
    }

    public UserDetails(int id, String ucn, String address, String country, String city, String cityPostCode, String street) {
        this.id = id;
        this.ucn = ucn;
        this.address = address;
        this.country = country;
        this.city = city;
        this.cityPostCode = cityPostCode;
        this.street = street;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUcn() {
        return ucn;
    }

    public void setUcn(String ucn) {
        this.ucn = ucn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCityPostCode() {
        return cityPostCode;
    }

    public void setCityPostCode(String cityPostCode) {
        this.cityPostCode = cityPostCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "UserDetails [userDetailsId=" + id + ", ucn=" + ucn + ", address=" + address + ", country=" + country + ", city=" + city
                + ", cityPostCode=" + cityPostCode + ", street=" + street + "]";
    }
}
