package com.javaruszglowa.stackgroup;


public class MyStackTester {
    public static void main(String[] args) {
        int stackSize = 4;
        MyStack  myStack = new MyStack(stackSize);

        // Przeglądanie pustego stosu
        myStack.printStack();

        // Wyświetleniestanu przed dodawaniem elementów
        System.out.println("Czy stos pusty: " + myStack.isEmpty());
        
        // Dodanie elementów
        System.out.println("\n ");
        System.out.println("Dodanie elementow do stosu");
        myStack.push("A");
        myStack.push("B");
        myStack.push("C");
        myStack.push("D");
         
        // Przeglądanie stosu
        myStack.printStack();

        // Usunięcie elementu ze stosu - usunie D
        System.out.println("\n ");
        System.out.println("Usuniecie ostatniego elementu");
        myStack.pop() ;

        // Stan po usunięiu
        myStack.printStack();

        // Dodanie elementu
        System.out.println("\n ");
        System.out.println("Dodanie E");
        myStack.push("E");
        // Przeglądanie stosu
        myStack.printStack();

        //  // Próba dodania kolejnych elementów ponad zakres stosu
        // System.out.println("\nPo próbie dodania elementu poza zakres stosu, oczekiwany wyjątek.");
        // try {
        //     myStack.push("F");
        // } catch (IllegalStateException e) {
        //     System.out.println("Błąd: " + e.getMessage());
        // }

        // Stan stosu po próbie dodania ponad zakres
        myStack.printStack();

     }
    }
