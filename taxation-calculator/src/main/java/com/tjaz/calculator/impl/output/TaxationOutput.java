package com.tjaz.calculator.impl.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxationOutput {

    private double possibleReturnAmount;
    private double possibleReturnAmountBefTax;
    private double possibleReturnAmountAfterTax;
    private double taxRate;
    private double taxAmount;
}
