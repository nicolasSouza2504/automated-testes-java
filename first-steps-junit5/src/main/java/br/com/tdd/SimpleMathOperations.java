package br.com.tdd;

import java.util.function.Supplier;

public class SimpleMathOperations {

    // Addition
    public Double sum(Double a, Double b) {
        return a + b;
    }

    // Subtraction
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    // Multiplication
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    // Division
    public Double divide(Double a, Double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return (double) a / b;
    }

    public String teste(Supplier<String> testes) {
        return testes.get();
    }
}
