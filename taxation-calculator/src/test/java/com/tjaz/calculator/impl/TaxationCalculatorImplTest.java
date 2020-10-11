package com.tjaz.calculator.impl;

import com.tjaz.calculator.entities.Trader;
import com.tjaz.calculator.impl.output.TaxationOutput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaxationCalculatorImplTest {

    private TaxationCalculatorImpl taxationCalculator;

    @Before
    public void setUp() {
        taxationCalculator = new TaxationCalculatorImpl();
    }


    // 7.5EUR - 2EUR = 5.5EUR => possible return amount is 5.5EUR
    @Test
    public void amount() {
        final Trader mockTrader = getMockTrader(null,
                                                null,
                                                null,
                                                null,
                                                2.0);
        final TaxationOutput amount = taxationCalculator.amount(mockTrader, 7.5);
        Assert.assertEquals(5.5, amount.getPossibleReturnAmount(), 0);
        Assert.assertEquals(5.5, amount.getPossibleReturnAmountAfterTax(), 0);
        Assert.assertEquals(7.5, amount.getPossibleReturnAmountBefTax(), 0);
        Assert.assertEquals(2, amount.getTaxRate(), 0);
        Assert.assertEquals(2, amount.getTaxAmount(), 0);
    }

    // 7.5EUR * 0.1 = 0.75EUR => possible return amount is 7.5EUR - 0.75EUR = 6.75
    @Test
    public void rate() {
        final Trader mockTrader = getMockTrader(null,
                                                null,
                                                null,
                                                null,
                                                10.0);
        final TaxationOutput rate = taxationCalculator.rate(mockTrader, 7.5);
        Assert.assertEquals(6.75, rate.getPossibleReturnAmount(), 0);
        Assert.assertEquals(6.75, rate.getPossibleReturnAmountAfterTax(), 0);
        Assert.assertEquals(7.5, rate.getPossibleReturnAmountBefTax(), 0);
        Assert.assertEquals(10, rate.getTaxRate(), 0);
        Assert.assertEquals(0.75, rate.getTaxAmount(), 0);
    }

    // 2.5EUR * 0.1 = 0.25EUR => possible return amount is 7.5EUR - 0.25EUR = 7.25EUR
    @Test
    public void rate_winnings() {
        final Trader mockTrader = getMockTrader(null,
                                                null,
                                                null,
                                                2.5,
                                                10.0);
        final TaxationOutput rate = taxationCalculator.rate(mockTrader, 7.5, 2.5);
        Assert.assertEquals(7.25, rate.getPossibleReturnAmount(), 0);
        Assert.assertEquals(7.25, rate.getPossibleReturnAmountAfterTax(), 0);
        Assert.assertEquals(7.5, rate.getPossibleReturnAmountBefTax(), 0);
        Assert.assertEquals(10, rate.getTaxRate(), 0);
        Assert.assertEquals(0.25, rate.getTaxAmount(), 0);
    }

    private Trader getMockTrader(final String method,
                                 final String type,
                                 final String tradeId,
                                 final Double winningAmount,
                                 final Double rate) {
        final Trader trader = new Trader();
        trader.setMethod(method);
        trader.setType(type);
        trader.setTraderId(tradeId);
        trader.setWinningAmount(winningAmount);
        trader.setRate(rate);
        return trader;
    }
}