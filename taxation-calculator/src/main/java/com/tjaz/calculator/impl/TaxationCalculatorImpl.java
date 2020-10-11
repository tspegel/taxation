package com.tjaz.calculator.impl;

import com.tjaz.calculator.entities.Trader;
import com.tjaz.calculator.impl.exception.TaxationCalculatorException;
import com.tjaz.calculator.impl.output.TaxationOutput;
import com.tjaz.calculator.impl.validators.TraderValidation;
import com.tjaz.calculator.repositiories.TraderRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@NoArgsConstructor
public class TaxationCalculatorImpl {

    public static final String TYPE_GENERAL = "general";
    public static final String TYPE_WINNINGS = "winnings";
    public static final String METHOD_RATE = "rate";
    public static final String METHOD_AMOUNT = "amount";
    private TraderRepository traderRepository;
    private TraderValidation validator;

    @Autowired
    public TaxationCalculatorImpl(final TraderRepository traderRepository,
                                  final TraderValidation validator) {
        this.traderRepository = traderRepository;
        this.validator = validator;
    }


    /**
     * Calculate Tax. OOutput is based on type (general/winnings)
     */
    public TaxationOutput taxationCalculator(final String traderId, final Double playedAmount, final Double odd)
            throws TaxationCalculatorException {
        if (StringUtils.isEmpty(traderId) || playedAmount == null || odd == null) {
            log.error("Trader id or playedAmount or odd param is missing.");
            throw new TaxationCalculatorException("One or more params are missing.");
        }

        final Double possibleReturnAmount = playedAmount * odd;
        final Trader trader = traderRepository.fetchTrader(traderId);
        validator.validateTrader(trader, traderId);

        final String type = trader.getType();
        if (TYPE_GENERAL.equalsIgnoreCase(type)) {
            return processGeneralTaxation(trader, possibleReturnAmount);
        } else if (TYPE_WINNINGS.equalsIgnoreCase(type)) {
            return processWinningTaxation(trader, possibleReturnAmount);
        } else {
            log.error(String.format("Trader with traderId %s is missing type", traderId));
            throw new TaxationCalculatorException("Type is missing.");
        }
    }

    /**
     * Process General taxation. Output is based on method (rate/amount)
     */
    private TaxationOutput processGeneralTaxation(final Trader trader, final Double possibleReturnAmount)
            throws TaxationCalculatorException {
        final String method = trader.getMethod();
        if (METHOD_RATE.equalsIgnoreCase(method)) {
            return rate(trader, possibleReturnAmount);
        } else if (METHOD_AMOUNT.equalsIgnoreCase(method)) {
            return amount(trader, possibleReturnAmount);
        } else {
            log.error(String.format("Method is missing from vendor with traderId %s", trader.getTraderId()));
            throw new TaxationCalculatorException("Method is missing.");
        }
    }

    /**
     * Process Winning taxation. Output is based on method (rate/amount)
     */
    private TaxationOutput processWinningTaxation(final Trader trader, final Double possibleReturnAmount) {
        final String method = trader.getMethod();
        final Double winningAmount = trader.getWinningAmount();
        final Double winningsPossibleReturnAmount = possibleReturnAmount - winningAmount;

        if (METHOD_RATE.equalsIgnoreCase(method)) {
            return rate(trader, possibleReturnAmount, winningsPossibleReturnAmount);
        } else if (METHOD_AMOUNT.equalsIgnoreCase(method)) {
            return amount(trader, winningsPossibleReturnAmount);
        } else {
            log.error(String.format("Method is missing from vendor with traderId %s", trader.getTraderId()));
            return null;
        }
    }

    public TaxationOutput rate(final Trader trader, final Double possibleReturnAmount) {
        return rate(trader, possibleReturnAmount, null);
    }

    /**
     * Calculate taxation based on percentage specific to Trader
     * It's calculated different if "winningsPossibleReturnAmount" is present
     */
    public TaxationOutput rate(final Trader trader,
                               final Double possibleReturnAmount,
                               final Double winningsPossibleReturnAmount) {
        final double taxRate = trader.getRate();
        final Double taxAmount;
        if (winningsPossibleReturnAmount == null) {
            taxAmount = possibleReturnAmount * (taxRate / 100);
        } else {
            taxAmount = winningsPossibleReturnAmount * (taxRate / 100);
        }
        final double possibleReturnAmountAfterTax = possibleReturnAmount - taxAmount;
        return new TaxationOutput(possibleReturnAmountAfterTax,
                                  possibleReturnAmount,
                                  possibleReturnAmountAfterTax,
                                  taxRate,
                                  taxAmount);
    }

    /**
     * Calculate taxation based on fixed amount
     */
    public TaxationOutput amount(final Trader trader, final Double possibleReturnAmount) {
        final Double taxRate = trader.getRate();
        final double possibleReturnAmountAfterTax = possibleReturnAmount - taxRate;
        return new TaxationOutput(possibleReturnAmountAfterTax,
                                  possibleReturnAmount,
                                  possibleReturnAmountAfterTax,
                                  taxRate,
                                  taxRate);
    }

}
