package com.javaruszglowa.model;
import com.javaruszglowa.model.Employee;
import com.javaruszglowa.model.Address;

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

    public int getPhysicalStrength() {
        return physicalStrength;
    }

    @Override
    public String toString() {
        return String.format("%s, Physical Strength: %d", 
            super.toString(), physicalStrength);
    }

}
