package com.javaruszglowa.stackgroup;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

public class MyStackTest {

    private MyStack stack;

    @BeforeEach
    public void setUp() {
        // Tworzenie stosu przed każdym testem
        stack = new MyStack(4);
    }

    // Test dodawania elementów
    @Test
    public void testPush() {
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");

        assertEquals("D", stack.peek(), "Szczyt stosu powinien być równy D");
    }

    // Test sprawdzania, czy stos jest pełny
    @Test
    public void testIsFull() {
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");

        assertTrue(stack.isFull(), "Stos powinien być pełny.");
    }

    // Test sprawdzania, czy stos jest pusty
    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty(), "Stos powinien być pusty na początku.");
    }

    // Test usuwania elementu ze stosu
    @Test
    public void testPop() {
        stack.push("A");
        stack.push("B");
        stack.push("C");

        String removedElement = stack.pop();
        assertEquals("C", removedElement, "Usunięty element powinien wynosić C.");
        assertEquals("B", stack.peek(), "Szczyt stosu po usunięciu elementu powinien wynosić B.");
    }

    // Test próby usunięcia elementu z pustego stosu (spodziewany wyjątek)
    @Test
    public void testPopEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        }, "Usunięcie elementu z pustego stosu powinno rzucić wyjątek NoSuchElementException.");
    }

    // Test próby dodania elementu, gdy stos jest pełny (spodziewany wyjątek)
    @Test
    public void testPushFullStack() {
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");

        assertThrows(IllegalStateException.class, () -> {
            stack.push("E");
        }, "Dodanie elementu do pełnego stosu powinno rzucić wyjątek IllegalStateException.");
    }

    // Test sprawdzania elementu na szczycie stosu
    @Test
    public void testPeek() {
        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals("C", stack.peek(), "Szczyt stosu powinien wynosić C.");
    }

    // Test próby podejrzenia elementu ze stosu, gdy stos jest pusty (spodziewany wyjątek)
    @Test
    public void testPeekEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        }, "Podejrzenie elementu ze stosu pustego powinno rzucić wyjątek NoSuchElementException.");
    }

    // Test przeglądania stosu
    @Test
    public void testPrintStack() {
        stack.push("A");
        stack.push("B");
        stack.push("C");

        stack.printStack(); // Sprawdzamy, czy działa poprawnie. Możemy porównać to z oczekiwanym wynikiem ręcznie.
    }
}
