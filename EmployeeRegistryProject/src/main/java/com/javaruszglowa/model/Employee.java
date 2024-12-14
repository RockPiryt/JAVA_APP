package com.javaruszglowa.model;

// Klasa ogólna Pracownik
//Abstrakcyjna żeby nie tworzyć takich obiektów, inne klasy po niej dziedzicza
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

    //aby moc odczytać wartość Id
    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getExperience() {
        return experience;
    }

    public Address getAddress() {
        return address;
    }

    public abstract double getValueForCorporation();

    @Override
    public String toString() {
        return "ID: " + id + ", " + firstName + " " + lastName + ", Age: " + age + ", Experience: " + experience + ", Address: " + address;
    }
}