package com.javaruszglowa.model;

import com.javaruszglowa.model.Employee;
import com.javaruszglowa.model.Address;
import com.javaruszglowa.model.OfficeEmployee;
import com.javaruszglowa.model.PhysicalEmployee;
import com.javaruszglowa.model.Salesman;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private Employee employee;
    private Address address;

    @BeforeEach
    void setUp() {
        // Tworzymy adres i pracownika poprzez instancję klasy pochodnej
        address = new Address("Marszalkowska", "5", "10", "Warsaw");
        employee = new OfficeEmployee("1", "Anna", "Kowalska", 30, 5, address, "P123", 100);
    }

    @Test
    void testGetters() {
        // Testujemy gettery z klasy abstrakcyjnej
        assertEquals("1", employee.getId());
        assertEquals("Kowalska", employee.getLastName());
        assertEquals(30, employee.getAge());
        assertEquals(5, employee.getExperience());
        assertEquals(address, employee.getAddress());
    }

    @Test
    void testToString() {
        // Sprawdzamy format toString z klasy abstrakcyjnej
        String expected = "ID: 1, Anna Kowalska, Age: 30, Experience: 5, Address: Marszalkowska, 5, 10, Warsaw, Position ID: P123, Intellect: 100";
        assertEquals(expected, employee.toString());
    }
}
