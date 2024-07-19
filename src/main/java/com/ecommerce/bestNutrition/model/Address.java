package com.ecommerce.bestNutrition.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "direcciones")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String firstName;
    private String lastName;
    private String streetAdrress;
    private String city;
    private String zipCode;
    private String state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    private String phone;

    public Address(){}

    public Address(Long id, String firstName, String lastName, String streetAdrress, String city, String zipCode, String state, User user, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAdrress = streetAdrress;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.user = user;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAdrress() {
        return streetAdrress;
    }

    public void setStreetAdrress(String streetAdrress) {
        this.streetAdrress = streetAdrress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
