package calculator;
//double 15miejsc po przecinku i dokładniejszy

public class Calculator {
    private double state = 0;
    private double memory = 0;
    private boolean errorState = false; 

    // Utility function to validate and parse double from String input
    public static double validateAndParseDouble(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: must be a number");
        }
    }

    public double getState() {
        return state;
    }

    public void setState(double state) {
        // odwołanie do state/errorState obiektu klasy calculator
        this.state = state; 
        this.errorState = false;
    }

    // Sprawdzenie czy jest błąd
    public boolean isErrorState() {
        return errorState;
    }

    // Resetowanie błędu
    public void clearErrorState() {
        errorState = false;
    }

    // Funkcje matematyczne-----------------------------------------------------

    public void add(double value) {
        if (!errorState) {
            state += value;
        }
    }

    public void mult(double value) {
        if (!errorState) {
            state *= value;
        }
    }

    // Nowe funkcje -----------------------------------------------------
    // Odejmowanie
    public void subtract(double value) {
        if (!errorState) {
            state -= value;
        }
    }

    // Dzielenie z wykluczeniem dzielenia przez 0
    public void divide(double value) throws IllegalArgumentException {
        if (value == 0) {
            errorState = true; //jak wyjatek to error
            throw new IllegalArgumentException("Dzielenie przez 0 niedozwolone!");
        } else {
            state /= value;
            errorState = false; 
        }
    }
    // Operacje na pamięci kalkulatora----------------------------------------------------------
    // Zapisanie aktualne stanu kalkulatora do pamięci
    public void storeToMemory() {
        if (!errorState) {
            memory = state;
        }
    }

    // Odczytuje wartość z pamięci kalkulatora.
    public double bringBackFromMemory() {
        return memory;
    }

    // Zeruje pamięć kalkulatora
    public void clearMemory() {
        memory = 0;
    }

    // Operacje z wykorzystaniem wartości z pamięci
    public void addFromMemory() {
        if (!errorState) {
            state += memory;
        }
    }

    public void subtractFromMemory() {
        if (!errorState) {
            state -= memory;
        }
    }

    public void multFromMemory() {
        if (!errorState) {
            state *= memory;
        }
    }

    // Division from memory with try-catch for division by zero handling
    public void divideFromMemory() {
        try {
            if (memory == 0) {
                throw new ArithmeticException("Dzielenie przez 0 niedozwolone!");
            }
            state /= memory;
        } catch (ArithmeticException e) {
            errorState = true;
            System.out.println("Błąd: " + e.getMessage());
        }
    }
    
}
