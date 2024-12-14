package com.javaruszglowa.model;
import com.javaruszglowa.model.Employee;
import com.javaruszglowa.model.Address;

public class PhysicalEmployee extends Employee {
    private int physicalStrength;

    public PhysicalEmployee(String id, String firstName, String lastName, int age, int experience, Address address, int physicalStrength) {
        super(id, firstName, lastName, age, experience, address);

        // Validate physicalStrength value
        if (physicalStrength < 1 || physicalStrength > 100) {
            throw new IllegalArgumentException("Physical strength must be between 1 and 100.");
        }
        
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
