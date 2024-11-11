package kalkulator;


import calculator.Calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
                         

public class CalculatorTest {

	private Calculator sut;
	private static final double DELTA = 1e-5; // Precision for floating-point comparisons


	    // Ustawienie kalkulatora przed każdym testem
	    @BeforeEach
	    public void setUp() {
	        sut = new Calculator();
	    }

		// Czyszczenie kalkulatora po każdym teście
		@AfterEach
		public void tearDown() {
			sut = null; 
		}
		//----------------------------------------Testy sprawdzające poprawność wprowadzanych danych
		// Test case for valid double input
		@Test
		public void testValidDoubleInput() {
			assertEquals(123.45, Calculator.validateAndParseDouble("123.45"), 1e-5, "Valid double string should parse correctly");
		}
	
		// Test case for valid integer input (as a string)
		@Test
		public void testValidIntegerInput() {
			assertEquals(50.0, Calculator.validateAndParseDouble("50"), 1e-5, "Valid integer string should parse correctly as double");
		}
	
		// Test case for invalid non-numeric input (text)
		@Test
		public void testInvalidStringInput() {
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Calculator.validateAndParseDouble("abc");
			});
			assertEquals("Invalid input: must be a number", exception.getMessage(), "Non-numeric string should throw an exception");
		}
	
		// Test case for input with mixed characters and numbers
		@Test
		public void testMixedInput() {
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Calculator.validateAndParseDouble("12abc34");
			});
			assertEquals("Invalid input: must be a number", exception.getMessage(), "Mixed input should throw an exception");
		}
	
		// Test case for special characters
		@Test
		public void testSpecialCharactersInput() {
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Calculator.validateAndParseDouble("!@#$%");
			});
			assertEquals("Invalid input: must be a number", exception.getMessage(), "Special characters should throw an exception");
		}
	
		// Test case for null input
		@Test
		public void testNullInput() {
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Calculator.validateAndParseDouble(null);
			});
			assertEquals("Input cannot be null", exception.getMessage(), "Null input should throw an exception");
		}
		
		// Test case for empty string input
		@Test
		public void testEmptyStringInput() {
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Calculator.validateAndParseDouble("");
			});
			assertEquals("Invalid input: must be a number", exception.getMessage(), "Empty string should throw an exception");
		}
	
		// Test case for whitespace input
		@Test
		public void testWhitespaceInput() {
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				Calculator.validateAndParseDouble("   ");
			});
			assertEquals("Invalid input: must be a number", exception.getMessage(), "Whitespace-only input should throw an exception");
		}

		//---------------------------------------------------------

	    // Test dla operacji dodawania 1 do początkowego stanu
	    @Test
	    public void testAddOne(){
	        sut.add(1.23400);
        	assertEquals(1.23400, sut.getState(), DELTA, "0 + 1.23400 = 1.23400");
	    }

	    // Test dla operacji mnożenia 1 przez 2
	    @Test
	    public void testMultOneByTwo(){
			sut.setState(1.44400);
			sut.mult(2.22200);
			assertEquals(3.20857, sut.getState(), DELTA, "1.444 * 2.222 = 2");
	    }

	    // Testy dla dodawania ----------------------------------

	    // Test dodawania zera
	    @Test
	    public void testAddZero() {
			sut.add(0.0);
			assertEquals(0.0, sut.getState(), DELTA, "0 + 0 = 0");
	    }

	    // Test dodawania liczby ujemnej do 0
	    @Test
	    public void testAddNegativeNumber() {
	        sut.add(-4.0);
        	assertEquals(-4.0, sut.getState(), DELTA, "0 + (-4) = -4");
	    }

	    // Test dodwania ujemnych z dodatnimi wartościami
	    @Test
	    public void testAddNegativeAndPositive() {
	        sut.setState(-10.0);
       	 	sut.add(12.0);
        	assertEquals(2.0, sut.getState(), DELTA, "-10 + 12 = 2");
	    }

	    // Test dodawania dużej liczby 
	    @Test
	    public void testAddLargeNumber() {
			double largeValue = Double.MAX_VALUE;
			sut.add(largeValue);
			assertEquals(Double.MAX_VALUE, sut.getState(), DELTA, "0 + MAX_VALUE = MAX_VALUE");
	    }

	    // Testy dla mnożenia ----------------------------------

	    // Test mnożenia przez zero
	    @Test
	    public void testMultByZero() {
	        sut.setState(1.0);
        	sut.mult(0.0);
        	assertEquals(0.0, sut.getState(), DELTA, "1 * 0 = 0");
	    }

	    // Test mnożenia przez liczbę ujemną
	    @Test
	    public void testMultByNegative() {
	        sut.setState(3.0);
        	sut.mult(-2.0);
        	assertEquals(-6.0, sut.getState(), DELTA, "3 * (-2) = -6");
	    }

	    // Test mnożenia przez dużą liczbę
	    @Test
	    public void testMultByLargeNumber() {
			sut.setState(1.0);
			double largeValue = Double.MAX_VALUE;
			sut.mult(largeValue);
			assertEquals(Double.MAX_VALUE, sut.getState(), DELTA, "1 * MAX_VALUE = MAX_VALUE");
	    }

	    // Testy dla odejmowania ----------------------------------

	    // Test odejmowania 2 liczby dodatnich
	    @Test
	    public void testSubtract() {
			sut.setState(8.0);
			sut.subtract(2.0);
			assertEquals(6.0, sut.getState(), DELTA, "8 - 2 = 6");
	    }

	    // Test odejmowania liczby ujemnej od dodatniej
	    @Test
	    public void testSubtractNegative() {
			sut.setState(11.0);
			sut.subtract(-7.0);
			assertEquals(18.0, sut.getState(), DELTA, "11 - (-7) = 18");
	    }

		// Testy dzielenia  ----------------------------------

	    // Test dzielenia przez liczbę dodatnią
	    @Test
	    public void testDivide() {
			sut.setState(20.0);
			sut.divide(2.0);
			
			assertAll("Division by positive number",
				() -> assertEquals(10.0, sut.getState(), DELTA, "20 / 2 = 10"),
				() -> assertFalse(sut.isErrorState(), "Calculator should not be in error state after dividing by 2.0")
			);
	    }

	    // Test dzielenia przez liczbę ujemną
	    @Test
	    public void testDivideByNegative() {
	        sut.setState(10.0);
        	sut.divide(-2.0);
			assertAll("Division by negative number",
				() -> assertEquals(-5.0, sut.getState(), DELTA, "10 / (-2) = -5"),
				() -> assertFalse(sut.isErrorState())
			);
	    }

		// Test dzielenia przez 0 - oczekiwanie na wyjatek 
		@Test
		public void testDivideByZero() {
			Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				sut.divide(0.0);  
			});
			assertAll("Division by 0",
			() -> assertEquals("Dzielenie przez 0 niedozwolone!", exception.getMessage()),
			() -> assertTrue(sut.isErrorState())
			);
		}

	    // Testy dla operacji pamięci kalkulatora ----------------------------------

	    // Test zapisywania do pamięci i odczytywania z pamięci
	    @Test
	    public void testStoreAndBringBackMemory() {
	        sut.setState(5.0);
        	sut.storeToMemory();

       		sut.setState(0.0);
       	 	sut.setState(sut.bringBackFromMemory());
        	assertEquals(5.0, sut.getState(), DELTA, "State should be 5 after bring back from memory");
	    }

	    // Test czyszczenia pamięci
	    @Test
	    public void testClearMemory() {
			sut.setState(10.0);
			sut.storeToMemory();
			sut.clearMemory();
			assertEquals(0.0, sut.bringBackFromMemory(), DELTA, "Memory should be cleared to 0");
	    }

	    // Test dodawania z pamięci
	    @Test
	    public void testAddFromMemory() {
	        sut.setState(5.0);
        	sut.storeToMemory();
        	sut.setState(10.0);
        	sut.addFromMemory();
        	assertEquals(15.0, sut.getState(), DELTA, "10 + 5 (from memory) = 15");
	    }

	    // Test odejmowania z pamięci
	    @Test
	    public void testSubtractFromMemory() {
			sut.setState(5.0);
			sut.storeToMemory();
			sut.setState(10.0);
			sut.subtractFromMemory();
			assertEquals(5.0, sut.getState(), DELTA, "10 - 5 (from memory) = 5");
	    }

	    // Test mnożenia z pamięci
	    @Test
	    public void testMultFromMemory() {
	        sut.setState(3.0);
			sut.storeToMemory();
			sut.setState(5.0);
			sut.multFromMemory();
			assertEquals(15.0, sut.getState(), DELTA, "5 * 3 (from memory) = 15");
	    }
	

		// Dzielenie liczby przez 0 z pamieci (
	    @Test
		public void testDivideFromMemoryByZero() {
			sut.setState(0.0);
			sut.storeToMemory();
			sut.setState(100.0);
			sut.divideFromMemory();
			assertTrue(sut.isErrorState());
		}

		// Poprawne dzielenie z użyciem pamięci
		@Test
		public void testDivideFromMemoryByNonZeroValue() {
			sut.setState(20.0);
			sut.storeToMemory();
			sut.setState(100.0);
			sut.divideFromMemory();
			assertAll("Division from memory by 0",
			() -> assertEquals(5.0, sut.getState(), DELTA, "100 / 20 (from memory) = 5"),
			() -> assertFalse(sut.isErrorState())
			);
		}

}
