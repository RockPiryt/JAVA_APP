package com.javaruszglowa.registry;

import com.javaruszglowa.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRegistryTest {
    private EmployeeRegistry registry;
    private List<Employee> employees;
    private List<Employee> newEmployees;
    private Address address1, address2, address3, address4;
    private Employee officeEmployee1, officeEmployee2, officeEmployee3;
    private Employee physicalEmployee1, physicalEmployee2, physicalEmployee3;
    private Employee salesman1, salesman2, salesman3;

    @BeforeEach
    void setUp() {
        // Inicjalizacja rejestru pracowników
        registry = new EmployeeRegistry();

        // Przykładowe adresy
        address1 = new Address("Adamowicza", "10", "5", "Warsaw");
        address2 = new Address("Rakoczego", "3", "12", "Krakow");
        address3 = new Address("Marszałkowska", "25", "8", "Warsaw");
        address4 = new Address("Nowa", "7", "1", "Gdansk");

        // public Employee(String id, String firstName, String lastName, int age, int experience, Address address)
        // Przykładowi pracownicy
        officeEmployee1 = new OfficeEmployee("1", "Anna", "Kowalska", 30, 5, address1, "P123", 120);
        physicalEmployee1 = new PhysicalEmployee("2", "Jan", "Nowak", 40, 10, address2, 80);
        salesman1 = new Salesman("3", "Piotr", "Zielinski", 35, 8, address1, Salesman.Effectiveness.HIGH, 15);
        officeEmployee2 = new OfficeEmployee("4", "Marek", "Lewandowski", 28, 3, address3, "P456", 90);
        physicalEmployee2 = new PhysicalEmployee("5", "Tomasz", "Wisniewski", 50, 20, address4, 70);
        salesman2 = new Salesman("6", "Agnieszka", "Dabrowska", 32, 6, address3, Salesman.Effectiveness.MEDIUM, 12);

        // Dodanie pracowników do rejestru
        employees = new ArrayList<>();
        employees.add(officeEmployee1);
        employees.add(physicalEmployee1);
        employees.add(salesman1);
        employees.add(officeEmployee2);
        employees.add(physicalEmployee2);
        employees.add(salesman2);

        registry.addEmployees(employees);
    }

    @AfterEach
    // Możliwość czyszczenia zasobów po każdym teście
    void tearDown() {
        registry = null;
    }

    void testGetEmployeesSortedByAge() {
        // Ponownie używamy tego samego rejestru i listy pracowników z setUp()
        List<Employee> sortedByAge = registry.getEmployeesSortedByAge();
    
        // Oczekiwana kolejność po sortowaniu wg wieku:
        assertEquals(28, sortedByAge.get(0).getAge());  // Marek
        assertEquals(30, sortedByAge.get(1).getAge());  // Anna
        assertEquals(32, sortedByAge.get(2).getAge());  // Agnieszka
        assertEquals(35, sortedByAge.get(3).getAge());  // Piotr
        assertEquals(40, sortedByAge.get(4).getAge());  // Jan
        assertEquals(50, sortedByAge.get(5).getAge());  // Tomasz
    }

    @Test
    void testGetEmployeesSortedByLastName() {
        // Ponownie używamy tego samego rejestru i listy pracowników z setUp()
        List<Employee> sortedByLastName = registry.getEmployeesSortedByLastName();

        // Oczekiwana kolejność po sortowaniu wg nazwiska alfabetycznie:
        assertEquals("Dabrowska", sortedByLastName.get(0).getLastName());  // Agnieszka
        assertEquals("Kowalska", sortedByLastName.get(1).getLastName());  // Anna
        assertEquals("Lewandowski", sortedByLastName.get(2).getLastName());  //Marek
        assertEquals("Nowak", sortedByLastName.get(3).getLastName());  // Jan
        assertEquals("Wisniewski", sortedByLastName.get(4).getLastName());  // Tomasz
        assertEquals("Zielinski", sortedByLastName.get(5).getLastName());  // Piotr
    }

    @Test
    void testRemoveEmployeeById() {
        // Sprawdzamy liczbę pracowników przed usunięciem
        assertEquals(6, registry.getEmployeesSortedByLastName().size());

        // Usuwamy pracownika o ID "2"
        registry.removeEmployeeById("2");

        // Sprawdzamy liczbę pracowników po usunięciu
        assertEquals(5, registry.getEmployeesSortedByLastName().size());

        // Sprawdzamy, czy pracownik o ID "2" został usunięty
        List<Employee> employees = registry.getEmployeesSortedByLastName();
        boolean employeeRemoved = true;
        for (Employee e : employees) {
            if (e.getId().equals("2")) {
                employeeRemoved = false;
                break;
            }
        }
        assertTrue(employeeRemoved, "Employee with ID 2 should be removed");
    }

    @Test
    void testRemoveEmployeeById_NotFound() {
        // Usuwamy pracownika o ID "999", którego nie ma w rejestrze
        registry.removeEmployeeById("999");

        // Sprawdzamy, że liczba pracowników się nie zmieniła
        assertEquals(6, registry.getEmployeesSortedByLastName().size());
    }

    @Test
    void testGetEmployeesSortedByExperience() {
        List<Employee> sorted = registry.getEmployeesSortedByExperience();
        // Trzech pracowników o największym doświadczeniu (sortowanie malejaco)
        assertEquals(20, sorted.get(0).getExperience()); 
        assertEquals(10, sorted.get(1).getExperience());
        assertEquals(8, sorted.get(2).getExperience());
    }

    @Test
    void testGetEmployeesFromCity() {
        List<Employee> fromWarsaw = registry.getEmployeesFromCity("Warsaw");
        assertEquals(4, fromWarsaw.size());
    }

    @Test
    void testGetEmployeesWithValueForCorporation() {
        List<String> employeesWithValue = registry.getEmployeesWithValueForCorporation();
        assertEquals(6, employeesWithValue.size());
    }

    @Test
    void testAddEmployeesList() {
        // Przygotowanie nowej listy pracowników
        Employee officeEmployee3 = new OfficeEmployee("7", "Krzysztof", "Wojcik", 38, 12, new Address("Nowa", "12", "3", "Krakow"), "P789", 100);
        Employee physicalEmployee3 = new PhysicalEmployee("8", "Marta", "Kaczmarek", 45, 15, new Address("Rakoczego", "4", "12", "Krakow"), 75);
        Employee salesman3 = new Salesman("9", "Alicja", "Sienkiewicz", 29, 4, new Address("Marszałkowska", "5", "12", "Warsaw"), Salesman.Effectiveness.LOW, 10);

        newEmployees = new ArrayList<>();
        newEmployees.add(officeEmployee3);
        newEmployees.add(physicalEmployee3);
        newEmployees.add(salesman3);

        // Dodajemy nową listę pracowników do rejestru
        registry.addEmployees(newEmployees);

        // Sprawdzamy, czy liczba pracowników zwiększyła się o 3
        assertEquals(9, registry.getEmployeesSortedByLastName().size());
    }
}
