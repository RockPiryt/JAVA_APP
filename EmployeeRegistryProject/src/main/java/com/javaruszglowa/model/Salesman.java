package com.javaruszglowa.model;
import com.javaruszglowa.model.Employee;
import com.javaruszglowa.model.Address;

public class Salesman extends Employee {
    public enum Effectiveness {
        LOW(60), MEDIUM(90), HIGH(120);

        private final int value;

        Effectiveness(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Effectiveness effectiveness;
    private double commissionRate;

    public Salesman(String id, String firstName, String lastName, int age, int experience, Address address, Effectiveness effectiveness, double commissionRate) {
        super(id, firstName, lastName, age, experience, address);
        this.effectiveness = effectiveness;
        this.commissionRate = commissionRate;
    }

    @Override
    public double getValueForCorporation() {
        // getvalue pobiera wartosc liczbowa przypisana do LOW/MID/High
        return getExperience() * effectiveness.getValue();
    }

    public Effectiveness getEffectiveness() {
        return effectiveness;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    @Override
    public String toString() {
        return String.format("%s, Effectiveness: %s, Commission Rate: %.0f%%", 
            super.toString(), effectiveness, commissionRate);
    }

}
