package com.javaruszglowa.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {
    @Test
    void testToString() {
        Address address = new Address("Main Street", "10", "5", "Warsaw");
        assertEquals("Main Street 10/5, Warsaw", address.toString());
    }

    @Test
    void testGetCity() {
        Address address = new Address("Main Street", "10", "5", "Warsaw");
        assertEquals("Warsaw", address.getCity());
    }
}
