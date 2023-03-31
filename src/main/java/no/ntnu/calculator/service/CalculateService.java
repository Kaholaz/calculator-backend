package no.ntnu.calculator.service;


import org.springframework.stereotype.Service;

@Service
public class CalculateService {
    public double calculate(double operand1, double operand2, char operator) {
        return switch (operator) {
            case '+' -> operand1 + operand2;
            case '-' -> operand1 - operand2;
            case '*' -> operand1 * operand2;
            case '/' -> operand1 / operand2;
            default -> throw new IllegalArgumentException("Operator not supported");
        };
    }


}
