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

    private String possibleReturnAmount;
    private String possibleReturnAmountBefTax;
    private String possibleReturnAmountAfterTax;
    private String taxRate;
    private String taxAmount;

}
