package com.javaruszglowa.model;

public class OfficeEmployee extends Employee {
    private String positionId;
    private int intellect;

    public OfficeEmployee(String id, String firstName, String lastName, int age, int experience, Address address, String positionId, int intellect) {
        //dziedziczone
        super(id, firstName, lastName, age, experience, address);
        //specyficzne własne
        this.positionId = positionId;
        this.intellect = intellect;
    }

    @Override
    public double getValueForCorporation() {
        return getExperience() * intellect;
    }

    @Override
    public String toString() {
        return super.toString() + ", Position ID: " + positionId + ", Intellect: " + intellect;
    }
}