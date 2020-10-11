package com.tjaz.calculator.rest;

import com.tjaz.calculator.impl.TaxationCalculatorImpl;
import com.tjaz.calculator.impl.exception.TaxationCalculatorException;
import com.tjaz.calculator.impl.output.TaxationOutput;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@RestController
public class TaxationCalculatorRestController {

    private TaxationCalculatorImpl taxationCalculator;

    @Autowired
    public TaxationCalculatorRestController(final TaxationCalculatorImpl taxationCalculator) {
        this.taxationCalculator = taxationCalculator;
    }

    /**
     * Returns calculated taxation for placed bet.
     *
     * @param traderId TraderId is unique id that represent specific Trader
     * @param playedAmount Represents played amount
     * @param odd Represent odds of winning the bet
     * @return TaxationOutput.class
     */
    @RequestMapping(value = "/taxation/taxation-calculator", method = RequestMethod.POST)
    public TaxationOutput taxationCalculator(@RequestParam(value = "traderId") final String traderId,
                                             @RequestParam(value = "playedAmount") final double playedAmount,
                                             @RequestParam(value = "odd") final double odd)
            throws TaxationCalculatorException {
        return taxationCalculator.taxationCalculator(traderId, playedAmount, odd);
    }
}
