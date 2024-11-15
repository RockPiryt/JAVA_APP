package com.javaruszglowa.rpngroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException; 


public class RPNTest {

    private RPN rpn;

    @BeforeEach
    public void setUp() {
        // Tworzenie instancji RPN z maksymalnym rozmiarem stosu 10
        rpn = new RPN(10);
    }

    // Test poprawnego obliczenia wyrażenia
    @Test
    public void testEvaluateSimpleExpression() {
        String expression = "3 4 +";
        int result = rpn.evaluate(expression);
        assertEquals(7, result, "Wynik: 7.");
    }

    // Test bardziej skomplikowanego wyrażenia
    @Test
    public void testEvaluateComplexExpression() {
        String expression = "3 4 + 2 *";
        int result = rpn.evaluate(expression);
        assertEquals(14, result, "Wynik: 14.");
    }

    // Test wyrażenia z dzieleniem
    @Test
    public void testEvaluateDivision() {
        String expression = "8 2 /";
        int result = rpn.evaluate(expression);
        assertEquals(4, result, "Wynik: 4.");
    }

    // Test dzielenia przez zero
    @Test
    public void testEvaluateDivisionByZero() {
        String expression = "8 0 /";
        assertThrows(ArithmeticException.class, () -> {
            rpn.evaluate(expression);
        }, "Dzielenie przez zero - wyjątek ArithmeticException.");
    }

    // Test wyrażenia z nieznanym operatorem
    @Test
    public void testUnknownOperator() {
        String expression = "3 4 %";
        assertThrows(IllegalArgumentException.class, () -> {
            rpn.evaluate(expression);
        }, "Nieznany operator - wyjątek IllegalArgumentException.");
    }

    // Test nieprawidłowego wyrażenia (brak liczb na stosie)
    @Test
    public void testInvalidExpressionEmptyStack() {
        String expression = "+";
        assertThrows(NoSuchElementException.class, () -> {
            rpn.evaluate(expression);
        }, "Nieprawidłowe wyrażenie - wyjątek NoSuchElementException.");
    }

    // Test nieprawidłowego wyrażenia (brak wyniku)
    @Test
    public void testInvalidExpressionNoResult() {
        String expression = "3 4";
        assertThrows(IllegalStateException.class, () -> {
            rpn.evaluate(expression);
        }, "Nieprawidłowe wyrażenie - wyjątek IllegalStateException.");
    }

    // Test pustego wyrażenia
    @Test
    public void testEmptyExpression() {
        String expression = "";
        assertThrows(NoSuchElementException.class, () -> {
            rpn.evaluate(expression);
        }, "Puste wyrażenie - wyjątek NoSuchElementException.");
    }

    // Test poprawnego wyrażenia z dużymi liczbami
    @Test
    public void testEvaluateLargeNumbers() {
        String expression = "1000 2000 + 3000 *";
        int result = rpn.evaluate(expression);
        assertEquals(9000000, result, "Wynik: 9000000.");
    }

    // Test obsługi liczby ujemnych
    @Test
    public void testEvaluateNegativeNumbers() {
        String expression = "-3 4 +";
        int result = rpn.evaluate(expression);
        assertEquals(1, result, "Wynik powinien być równy 1.");
    }
}
