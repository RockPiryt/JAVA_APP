package com.javaruszglowa.model;

import com.javaruszglowa.model.Address;

// Klasa ogólna Pracownik
// Abstrakcyjna żeby nie tworzyć takich obiektów, inne klasy po niej dziedzicza
public abstract class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private int experience;
    private Address address;

    public Employee(String id, String firstName, String lastName, int age, int experience, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.experience = experience;
        this.address = address;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Getter for firstName (missing in your version)
    public String getFirstName() {
        return firstName;
    }

    // Getter for lastName
    public String getLastName() {
        return lastName;
    }

    // Getter for age
    public int getAge() {
        return age;
    }

    // Getter for experience
    public int getExperience() {
        return experience;
    }

    // Getter for address
    public Address getAddress() {
        return address;
    }

    // Abstract method to be implemented by child classes
    public abstract double getValueForCorporation();

    @Override
    public String toString() {
        return String.format("ID: %s, %s %s, Age: %d, Experience: %d, Address: %s", 
            id, firstName, lastName, age, experience, address.toString());
    }


}
