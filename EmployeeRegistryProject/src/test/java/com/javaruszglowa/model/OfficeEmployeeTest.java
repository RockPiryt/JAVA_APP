package com.javaruszglowa.model;

import com.javaruszglowa.model.Employee;
import com.javaruszglowa.model.Address;
import com.javaruszglowa.model.OfficeEmployee;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OfficeEmployeeTest {
    private Address address;
    private OfficeEmployee officeEmployee;

    @BeforeEach
    void setUp() {
        // Inicjalizacja przykładowego adresu i pracownika biurowego
        address = new Address("Marszalkowska", "10", "5", "Warsaw");
        officeEmployee = new OfficeEmployee("1", "Anna", "Kowalska", 30, 5, address, "P123", 120);
    }

    @Test
    void testConstructorAndGetters() {
        // Sprawdzanie wartości pól z użyciem obiektu z setup
        assertEquals("1", officeEmployee.getId());
        assertEquals("Anna", officeEmployee.getFirstName());
        assertEquals("Kowalska", officeEmployee.getLastName());
        assertEquals(30, officeEmployee.getAge());
        assertEquals(5, officeEmployee.getExperience());
        assertEquals(address, officeEmployee.getAddress());
        assertEquals("P123", officeEmployee.getPositionId());
        assertEquals(120, officeEmployee.getIntellect());
    }

    @Test
    void testGetValueForCorporation() {
        // Obliczanie wartości korporacyjnej z wykorzystaniem setup
        double expectedValue = 5 * 120; // experience * intellect
        assertEquals(expectedValue, officeEmployee.getValueForCorporation());
    }

    @Test
    void testToString() {
        // Porównanie z oczekiwanym formatem toString
        String expected = "ID: 1, Anna Kowalska, Age: 30, Experience: 5, Address: Marszalkowska, 10, 5, Warsaw, Position ID: P123, Intellect: 120";
        assertEquals(expected, officeEmployee.toString());
    }
}
