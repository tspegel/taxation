package com.tjaz.calculator.impl.validators;

import com.tjaz.calculator.entities.Trader;
import com.tjaz.calculator.impl.exception.TaxationCalculatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class TraderValidation {

    public static final String WINNINGS = "winnings";

    public void validateTrader(final Trader trader, final String traderId) throws TaxationCalculatorException {
        if (trader == null) {
            log.error(String.format("Trader is null when querying with traderId: %s", traderId));
            throw new TaxationCalculatorException("Trader is missing in database.");
        }

        final String type = trader.getType();
        if (StringUtils.isEmpty(type)) {
            log.error(String.format("Trader with traderId %s is missing type.", traderId));
            throw new TaxationCalculatorException("Type is missing.");
        }
        if (WINNINGS.equalsIgnoreCase(type)) {
            if (trader.getWinningAmount() == null) {
                log.error(String.format("Trader with traderId %s is missing winning amount.", traderId));
                throw new TaxationCalculatorException("Winning amount is missing.");
            }
        }

        if (StringUtils.isEmpty(trader.getMethod())) {
            log.error(String.format("Trader with traderId %s is missing method", traderId));
            throw new TaxationCalculatorException("Method is missing");
        }

        if (trader.getRate() == null) {
            log.error(String.format("Trader with traderId %s is missing rate", traderId));
            throw new TaxationCalculatorException("Rate is missing.");
        }
    }

}
