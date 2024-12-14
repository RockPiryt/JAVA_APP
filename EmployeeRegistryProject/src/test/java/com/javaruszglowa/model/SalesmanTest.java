package com.javaruszglowa.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SalesmanTest {
    @Test
    void testValueForCorporation() {
        Address address = new Address("Main Street", "10", "5", "Warsaw");
        Salesman salesman = new Salesman("1", "John", "Doe", 30, 5, address, Salesman.Effectiveness.HIGH, 20);
        assertEquals(600.0, salesman.getValueForCorporation());
    }
}
