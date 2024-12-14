package com.javaruszglowa.model;

import com.javaruszglowa.model.Employee;
import com.javaruszglowa.model.Address;



public class OfficeEmployee extends Employee {
    private String positionId;
    private int intellect;

    public OfficeEmployee(String id, String firstName, String lastName, int age, int experience, Address address, String positionId, int intellect) {
        super(id, firstName, lastName, age, experience, address);
        this.positionId = positionId;
        this.intellect = intellect;
    }

    @Override
    public double getValueForCorporation() {
        return getExperience() * intellect;
    }

    public String getPositionId() {
        return positionId;
    }

    public int getIntellect() {
        return intellect;
    }

    @Override
    public String toString() {
        return String.format("%s, Position ID: %s, Intellect: %d", 
            super.toString(), positionId, intellect);
    }

}
