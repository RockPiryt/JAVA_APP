package com.javaruszglowa.rpngroup;


public class RPNTester {
    public static void main(String[] args) {
        RPN rpncalc = new RPN(10);
        String expression = "3 4 + 2 *"; // notacja postfixowa

        try {
            int result = rpncalc.evaluate(expression);
            System.out.println("Wynik: " + result);  // Oczekiwany Wynik: 14
        } catch (Exception e) {
            System.err.println("Błąd: " + e.getMessage());
        }
    }
}
