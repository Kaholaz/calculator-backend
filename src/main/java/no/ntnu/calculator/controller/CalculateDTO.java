package no.ntnu.calculator.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateDTO {
    private double firstNumber;
    private double secondNumber;
    private String operator;
}
