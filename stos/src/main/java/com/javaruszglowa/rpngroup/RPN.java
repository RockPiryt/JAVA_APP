package com.javaruszglowa.rpngroup;

import com.javaruszglowa.stackgroup.MyStack;

import java.util.NoSuchElementException;

public class RPN {

    private MyStack stack;

    public RPN(int size) {
        // Inicjalizacja stosu z klasy MyStack
        stack = new MyStack(size);
    }

    
    // Oblicza wyrażenia arytmetycznego w postaci postfixowej
    public int evaluate(String expression) {

        //Rzucenie wyjątku jak wyrażenie puste lub składa sie z białych znaków (trim usuwa białe znaki z początka i końca)
        if (expression == null || expression.trim().isEmpty()) {
            throw new NoSuchElementException("Puste wyrażenie nie jest dozwolone.");
        }

        String[] tokens = expression.split(" ");

        for (String token : tokens) {
            // Jeśli liczba to dodajmy na stos
            if (isNumeric(token)) {
                
                stack.push(token);
            // Jeśli operatot to zdjęcie 2 liczb ze stosu
            } else if (isOperator(token)) {
                int b = Integer.parseInt(stack.pop());
                int a = Integer.parseInt(stack.pop());

                // Wykonanie operacji np.dodawania
                int result = performOperation(a, b, token);

                // Dodanie wyniku na stos
                stack.push(String.valueOf(result));
            } else {
                throw new IllegalArgumentException("Nieznany token: " + token);
            }
        }

        // Sprawdzenie, czy stos zawiera tylko jeden wynik, ma nie być pusty a  wynik ma być rózny od null
        if (stack.isEmpty() || stack.peek() == null) {
            throw new NoSuchElementException("Wyrażenie jest nieprawidłowe, brak wyniku.");
        }
        // pop zdjemuje ze stosu i  zwraca wartość która jest Stiringiem , dlatego konwertujemy na int
        int finalResult = Integer.parseInt(stack.pop());
        if (!stack.isEmpty()) {
            throw new IllegalStateException("Wyrażenie jest nieprawidłowe, stos nie jest pusty.");
        }

        return finalResult;
    }

    
    // Sprawdzenie czy podany string to liczba
    private boolean isNumeric(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Sprawdzenie czy string to któryś ze znaków
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    /**
     * Wykonuje operację matematyczną na dwóch liczbach.
     *
     * @param a        pierwsza liczba
     * @param b        druga liczba
     * @param operator operator (+, -, *, /)
     * @return wynik operacji
     */
    // Wykonanie operacji matematyczbtch
    private int performOperation(int a, int b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Dzielenie przez zero.");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Nieznany operator: " + operator);
        }
    }
}
