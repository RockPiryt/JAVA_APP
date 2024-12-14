package com.javaruszglowa.registry;

import com.javaruszglowa.model.Employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EmployeeRegistry {
    // Lista pracowników
    private List<Employee> employees = new ArrayList<>();

    // Dodanie pojedynczego pracownika
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        employees.add(employee);
    }

    // Dodanie listy pracowników
    public void addEmployees(List<Employee> employeesToAdd) {
        if (employeesToAdd == null) {
            throw new IllegalArgumentException("Employee list cannot be null.");
        }
        // Sprawdzenie każdego pracownika w liście, czy nie jest nullem
        for (Employee employee : employeesToAdd) {
            if (employee == null) {
                throw new IllegalArgumentException("Employee cannot be null.");
            }
        }
        employees.addAll(employeesToAdd);
    }
    

    // Usunięcie pracownika po ID
    public void removeEmployeeById(String id) {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getId().equals(id)) {
                iterator.remove(); // Usunięcie elementu za pomocą iteratora
                break;
            }
        }
    }

    // ----------------------------------------------------------Sortowanie z użyciem comparatora
    // Sortowanie malejąco wg doświadczenia za pomocą Comparator
    public List<Employee> getEmployeesSortedByExperience() {
        employees.sort(new ExperienceComparator());
        return new ArrayList<>(employees);
    }

    // Sortowanie rosnąco wg wieku za pomocą Comparator
    public List<Employee> getEmployeesSortedByAge() {
        employees.sort(new AgeComparator());
        return new ArrayList<>(employees);
    }

    // Sortowanie alfabetycznie wg nazwiska za pomocą Comparator
    public List<Employee> getEmployeesSortedByLastName() {
        employees.sort(new LastNameComparator());
        return new ArrayList<>(employees);
    }

    // Pracownicy z danego miasta
    public List<Employee> getEmployeesFromCity(String city) {
        List<Employee> result = new ArrayList<>();
        
        // Iteracja przez wszystkich pracowników
        for (Employee e : employees) {
            if (e.getAddress().getCity().equalsIgnoreCase(city)) {
                result.add(e);
            }
        }
        
        return result;
    }
    

    // Pracownicy z wartością dla korporacji
    public List<String> getEmployeesWithValueForCorporation() {
        List<String> result = new ArrayList<>();
        
        // Iteracja przez wszystkich pracowników
        for (Employee e : employees) {
            String employeeInfo = e.toString() + ", Value for Corporation: " + e.getValueForCorporation();
            result.add(employeeInfo);
        }
        
        return result;
    }
    
    // --------------------------------------------------------------Klasy Comparator dla sortowania
    static class ExperienceComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee e1, Employee e2) {
            return Integer.compare(e2.getExperience(), e1.getExperience());
        }
    }

    static class AgeComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee e1, Employee e2) {
            return Integer.compare(e1.getAge(), e2.getAge());
        }
    }

    static class LastNameComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee e1, Employee e2) {
            return e1.getLastName().compareTo(e2.getLastName());
        }
    }
}
