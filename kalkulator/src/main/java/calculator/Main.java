package calculator;

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        // Display initial state
        System.out.printf("Poczatkowy stan: %.2f%n", calc.getState());

        // Add operation
        calc.add(5.0);
        System.out.printf("Po dodaniu 5, stan to: %.2f%n", calc.getState());

        // Multiply operation
        calc.mult(2.0);
        System.out.printf("Po mnozeniu przez 2, stan to: %.2f%n", calc.getState());

        // Subtract operation
        calc.subtract(3.0);
        System.out.printf("Po odejmowaniu 3, stan to: %.2f%n", calc.getState());

        // Division with error check
        calc.divide(2.0);
        System.out.printf("Po dzieleniu przez 2, stan to: %.2f%n", calc.getState());
        System.out.println("Czy jest blad? " + calc.isErrorState());

        // Test division by zero to trigger errorState
        try {
            calc.divide(0.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Wykryto blad dzielenia przez zero: " + e.getMessage());
        }
        System.out.println("Czy jest blad? " + calc.isErrorState());
        calc.clearErrorState(); 
        System.out.println("Stan bledu wyczyszczony. Czy jest blad? " + calc.isErrorState());

        // Memory operations
        calc.storeToMemory();
        System.out.printf("Zapisane w pamieci: %.2f%n", calc.bringBackFromMemory());

        // Add to current state
        calc.add(10.0);
        System.out.printf("Po dodaniu 10, stan to: %.2f%n", calc.getState());

        // Subtract from memory
        calc.subtractFromMemory();
        System.out.printf("Po odjeciu z pamieci, stan to: %.2f%n", calc.getState());

        // Multiply by memory
        calc.multFromMemory();
        System.out.printf("Po mnozeniu przez wartosc z pamieci, stan to: %.2f%n", calc.getState());

        // Division by memory with error check
        calc.divideFromMemory();
        System.out.printf("Po dzieleniu przez wartosc z pamieci, stan to: %.2f%n", calc.getState());
        System.out.println("Czy jest blad? " + calc.isErrorState());

        // Clear memory and display final states
        calc.clearMemory();
        System.out.printf("Pamiec wyczyszczona. Aktualna pamiec: %.2f%n", calc.bringBackFromMemory());
        System.out.printf("Koncowy stan: %.2f%n", calc.getState());
    }
}
