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

    @Test
    void testValidIntellect() {
        // Test valid intellect values within the range 70-150
        assertDoesNotThrow(() -> new OfficeEmployee("1", "Anna", "Kowalska", 30, 5, new Address("Street", "10", "5", "City"), "P123", 100));
        assertDoesNotThrow(() -> new OfficeEmployee("2", "Marek", "Lewandowski", 28, 3, new Address("Street", "15", "7", "City"), "P456", 150));
    }

    @Test
    void testInvalidIntellectTooLow() {
        // Test invalid intellect values below the valid range
        assertThrows(IllegalArgumentException.class, () -> new OfficeEmployee("3", "Piotr", "Zielinski", 35, 8, new Address("Street", "12", "2", "City"), "P789", 60));
    }

    @Test
    void testInvalidIntellectTooHigh() {
        // Test invalid intellect values above the valid range
        assertThrows(IllegalArgumentException.class, () -> new OfficeEmployee("4", "Agnieszka", "Dabrowska", 32, 6, new Address("Street", "3", "12", "City"), "P101", 160));
    }
}
