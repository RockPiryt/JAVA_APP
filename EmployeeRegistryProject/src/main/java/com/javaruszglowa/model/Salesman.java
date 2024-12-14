package com.javaruszglowa.model;

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
    private int commissionRate;

    public Salesman(String id, String firstName, String lastName, int age, int experience, Address address, Effectiveness effectiveness, int commissionRate) {
        super(id, firstName, lastName, age, experience, address);
        this.effectiveness = effectiveness;
        this.commissionRate = commissionRate;
    }

    @Override
    public double getValueForCorporation() {
        // getvalue pobiera wartosc liczbowa przypisana do LOW/MID/High
        return getExperience() * effectiveness.getValue();
    }

    @Override
    public String toString() {
        return super.toString() + ", Effectiveness: " + effectiveness + ", Commission Rate: " + commissionRate + "%";
    }
}

