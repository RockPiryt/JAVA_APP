package com.javaruszglowa.model;

public class PhysicalEmployee extends Employee {
    private int physicalStrength;

    public PhysicalEmployee(String id, String firstName, String lastName, int age, int experience, Address address, int physicalStrength) {
        super(id, firstName, lastName, age, experience, address);
        this.physicalStrength = physicalStrength;
    }

    @Override
    public double getValueForCorporation() {
        return getExperience() * (double) physicalStrength / getAge();
    }

    @Override
    public String toString() {
        return super.toString() + ", Physical Strength: " + physicalStrength;
    }
}
