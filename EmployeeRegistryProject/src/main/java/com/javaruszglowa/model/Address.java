package com.javaruszglowa.model;

// Adres budynku
public class Address {
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String city;

    public Address(String street, String buildingNumber, String apartmentNumber, String city) {
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return street + " " + buildingNumber + "/" + apartmentNumber + ", " + city;
    }
}