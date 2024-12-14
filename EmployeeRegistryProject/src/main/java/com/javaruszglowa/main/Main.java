package com.javaruszglowa.main;

import com.javaruszglowa.model.*;
import com.javaruszglowa.registry.EmployeeRegistry;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Tworzenie instancji rejestru pracowników
        EmployeeRegistry registry = new EmployeeRegistry();

        // Przykładowe adresy
        Address address1 = new Address("Adamowicza", "10", "5", "Warsaw");
        Address address2 = new Address("Rakoczego", "3", "12", "Krakow");
        Address address3 = new Address("Marszałkowska", "25", "8", "Warsaw");
        Address address4 = new Address("Nowa", "7", "1", "Gdansk");

        // Przykładowi pracownicy
        Employee officeEmployee1 = new OfficeEmployee("1", "Anna", "Kowalska", 30, 5, address1, "P123", 120);
        Employee physicalEmployee1 = new PhysicalEmployee("2", "Jan", "Nowak", 40, 10, address2, 80);
        Employee salesman1 = new Salesman("3", "Piotr", "Zielinski", 35, 8, address1, Salesman.Effectiveness.HIGH, 15);

        Employee officeEmployee2 = new OfficeEmployee("4", "Marek", "Lewandowski", 28, 3, address3, "P456", 90);
        Employee physicalEmployee2 = new PhysicalEmployee("5", "Tomasz", "Wisniewski", 50, 20, address4, 70);
        Employee salesman2 = new Salesman("6", "Agnieszka", "Dabrowska", 32, 6, address3, Salesman.Effectiveness.MEDIUM, 12);

        // Dodawanie pracowników do rejestru
        registry.addEmployee(officeEmployee1);
        registry.addEmployee(physicalEmployee1);
        registry.addEmployee(salesman1);
        registry.addEmployee(officeEmployee2);
        registry.addEmployee(physicalEmployee2);
        registry.addEmployee(salesman2);

        // Wyświetlanie posortowanych wyników
        System.out.println("Employees sorted by experience:");
        registry.getEmployeesSortedByExperience().forEach(System.out::println);

        System.out.println("\nEmployees sorted by age:");
        registry.getEmployeesSortedByAge().forEach(System.out::println);

        System.out.println("\nEmployees sorted by last name:");
        registry.getEmployeesSortedByLastName().forEach(System.out::println);

        System.out.println("\nEmployees from Warsaw:");
        registry.getEmployeesFromCity("Warsaw").forEach(System.out::println);

        System.out.println("\nEmployees with value for corporation:");
        registry.getEmployeesWithValueForCorporation().forEach(System.out::println);
    }
}
