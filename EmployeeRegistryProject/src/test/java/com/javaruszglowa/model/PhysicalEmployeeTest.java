package com.javaruszglowa.model;

import com.javaruszglowa.model.Employee;
import com.javaruszglowa.model.Address;
import com.javaruszglowa.model.PhysicalEmployee;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhysicalEmployeeTest {
    private Address address;
    private PhysicalEmployee physicalEmployee;

    @BeforeEach
    void setUp() {
        // Inicjalizacja przykładowego adresu i pracownika fizycznego
        address = new Address("Nowa", "15", "7", "Gdansk");
        physicalEmployee = new PhysicalEmployee("2", "Jan", "Nowak", 40, 10, address, 80);
    }

    @Test
    void testConstructorAndGetters() {
        // Sprawdzanie wartości pól z użyciem obiektu z setup
        assertEquals("2", physicalEmployee.getId());
        assertEquals("Jan", physicalEmployee.getFirstName());
        assertEquals("Nowak", physicalEmployee.getLastName());
        assertEquals(40, physicalEmployee.getAge());
        assertEquals(10, physicalEmployee.getExperience());
        assertEquals(address, physicalEmployee.getAddress());
        assertEquals(80, physicalEmployee.getPhysicalStrength());
    }

    @Test
    void testGetValueForCorporation() {
        // Obliczanie wartości korporacyjnej z wykorzystaniem setup
        double expectedValue = 10 * (80.0 / 40); // experience * (physicalStrength / age)
        assertEquals(expectedValue, physicalEmployee.getValueForCorporation());
    }

    @Test
    void testToString() {
        // Porównanie z oczekiwanym formatem toString
        String expected = "ID: 2, Jan Nowak, Age: 40, Experience: 10, Address: Nowa, 15, 7, Gdansk, Physical Strength: 80";
        assertEquals(expected, physicalEmployee.toString());
    }
}
