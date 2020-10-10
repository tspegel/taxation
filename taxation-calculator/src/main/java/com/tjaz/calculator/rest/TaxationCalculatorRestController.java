package com.tjaz.calculator.rest;

import com.tjaz.calculator.impl.output.TaxationOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaxationCalculatorRestController {

    @RequestMapping(value = "/taxation/taxation-calculator", method = RequestMethod.POST)
    public TaxationOutput addSubject(@RequestParam(value = "traderId") final String traderId,
                                     @RequestParam(value = "playedAmount") final String playedAmount,
                                     @RequestParam(value = "odd") final String odd) {
        return new TaxationOutput("dasd",
                                  "dasd",
                                  "dsadas",
                                  "dsadas",
                                  "dsadas");
    }

}
