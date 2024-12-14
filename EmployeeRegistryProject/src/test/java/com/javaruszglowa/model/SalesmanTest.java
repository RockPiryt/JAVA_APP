package com.javaruszglowa.model;
import com.javaruszglowa.model.Employee;
import com.javaruszglowa.model.Address;
import com.javaruszglowa.model.Salesman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalesmanTest {
    private Address address;
    private Salesman salesman;

    @BeforeEach
    void setUp() {
        // Inicjalizacja przykładowego adresu i pracownika Salesman
        address = new Address("Marszalkowska", "5", "10", "Warsaw");
        salesman = new Salesman("3", "Piotr", "Zielinski", 35, 8, address, Salesman.Effectiveness.HIGH, 15);
    }

    @Test
    void testConstructorAndGetters() {
        // Sprawdzanie wartości pól z użyciem obiektu z setup
        assertEquals("3", salesman.getId());
        assertEquals("Piotr", salesman.getFirstName());
        assertEquals("Zielinski", salesman.getLastName());
        assertEquals(35, salesman.getAge());
        assertEquals(8, salesman.getExperience());
        assertEquals(address, salesman.getAddress());
        assertEquals(Salesman.Effectiveness.HIGH, salesman.getEffectiveness());
        assertEquals(15, salesman.getCommissionRate());
    }

    @Test
    void testGetValueForCorporation() {
        // Obliczanie wartości korporacyjnej z wykorzystaniem setup
        double expectedValue = 8 * 120; // experience * HIGH (120)
        assertEquals(expectedValue, salesman.getValueForCorporation());
    }

    @Test
    void testToString() {
        // Porównanie z oczekiwanym formatem toString
        String expected = "ID: 3, Piotr Zielinski, Age: 35, Experience: 8, Address: Marszalkowska, 5, 10, Warsaw, Effectiveness: HIGH, Commission Rate: 15%";
        assertEquals(expected, salesman.toString());
    }

    @Test
    void testValidSalesman() {
        // Valid Effectiveness and commissionRate within the range 1-100
        assertDoesNotThrow(() -> new Salesman("1", "Anna", "Kowalska", 30, 5, new Address("Street", "10", "5", "City"), Salesman.Effectiveness.LOW, 50));
        assertDoesNotThrow(() -> new Salesman("2", "Marek", "Lewandowski", 28, 3, new Address("Street", "15", "7", "City"), Salesman.Effectiveness.HIGH, 100));
    }

    @Test
    void testInvalidCommissionRateTooLow() {
        // Invalid commissionRate less than 1
        assertThrows(IllegalArgumentException.class, () -> new Salesman("3", "Piotr", "Zielinski", 35, 8, new Address("Street", "12", "2", "City"), Salesman.Effectiveness.MEDIUM, 0));
    }

    @Test
    void testInvalidCommissionRateTooHigh() {
        // Invalid commissionRate greater than 100
        assertThrows(IllegalArgumentException.class, () -> new Salesman("4", "Agnieszka", "Dabrowska", 32, 6, new Address("Street", "3", "12", "City"), Salesman.Effectiveness.LOW, 101));
    }

    @Test
    void testInvalidEffectivenessNull() {
        // Null Effectiveness (should not happen since it's an enum, but let's ensure)
        assertThrows(IllegalArgumentException.class, () -> new Salesman("5", "Tomasz", "Wisniewski", 40, 10, new Address("Street", "5", "6", "City"), null, 50));
    }
}
