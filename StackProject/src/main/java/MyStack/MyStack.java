package MyStack;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyStack {

   private int maxSize; //5
   private String[] stackArray;
   private int top;// Wskaźnik na wierzchołek stosu

   public MyStack(int size) {
       this.maxSize = size;
       stackArray = new String[maxSize];
       top = -1;// pusta lista
   }

   // Dodanie 1 elementu na stos
   public String push (String element) {
     // Stos pełny rzucenie wyjątku - operacja jest nielegalna ze względu na stan obiektu.
     if (isFull()) {
        throw new IllegalStateException("Stos jest pelny. Nie mozna dodac elementu.");
    }
    
    top++; // Zwiększamy wskaźnik `top` i dodajemy element.
    stackArray[top] = element;
    return element;
   }

   // Usunięcie elementu ze stosu 
   public String pop() {
    // stos pusty - rzucenie wyjątku NoSuchElementException
    if (isEmpty()) {
        throw new NoSuchElementException("Stos jest pusty. Nie można usunąć elementu.");
    }
    String removedElement = stackArray[top];   // Pobranie elementu na szczycie stosu
    stackArray[top] = null;     // Usunięcie elementu (ustawiamy na null, aby wskazać, że miejsce jest puste)
    top--;// Zmniejszamy wskaźnik top, wskazując na nowy wierzchołek stosu
    System.out.println("Usunieto: " + removedElement);
    return removedElement;
}
   // zwraca wartość elementu na szczycie stosu
   public String peek() {
        // stos pusty- rzucenie wyjatku
       if (isEmpty()) {
           throw new NoSuchElementException("Stos jest pusty. Brak elementu na szczycie.");
       }
       return stackArray[top];
   }

   // Sprawdza, czy stos jest pusty — zwraca true, jeśli wskaźnik top wynosi -1.
   public boolean isEmpty() {
        if (top == -1) {
            return true;
        } else {
            return false;
        }
    }

   //Sprawdza, czy stos jest pełny — zwraca true, jeśli top jest równy maxSize - 1.
   public boolean isFull() {
        if (top == maxSize - 1) {
            return true; // Stos jest pełny
        } else {
            return false; // Stos nie jest pełny
        }
   }

   public void printStack(){
        System.out.printf("Stos: \n" );
        System.out.println(Arrays.toString(stackArray));
   }
}