package com.javaruszglowa.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    @Test
    void testToString() {
        // Create address objects
        Address address1 = new Address("Adamowicza", "10", "5", "Warsaw");
        Address address2 = new Address("Rakoczego", "3", "12", "Krakow");
        Address address3 = new Address("Marszałkowska", "25", "8", "Warsaw");
        Address address4 = new Address("Nowa", "7", "1", "Gdansk");

        // Test toString method for each address
        assertEquals("Adamowicza, 10, 5, Warsaw", address1.toString());
        assertEquals("Rakoczego, 3, 12, Krakow", address2.toString());
        assertEquals("Marszałkowska, 25, 8, Warsaw", address3.toString());
        assertEquals("Nowa, 7, 1, Gdansk", address4.toString());
    }

    @Test
    void testGetCity() {
        Address address = new Address("Main Street", "10", "5", "Warsaw");
        assertEquals("Warsaw", address.getCity());
    }
}

