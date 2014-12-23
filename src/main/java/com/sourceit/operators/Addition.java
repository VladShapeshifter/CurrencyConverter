package com.sourceit.operators;

import com.sourceit.exceptions.WrongCalculationOperator;
import com.sourceit.models.Currency;

/**
 * Created by Vladislav on 22.12.2014.
 */
public class Addition implements StandardMathOperator {
    @Override
    public Currency eval(Currency cur1, Currency cur2) throws WrongCalculationOperator {
        if (!cur1.getType().equals(cur2.getType())) {
            throw new WrongCalculationOperator("Different currencies cannot be subtracted.");
        }
        return new Currency(cur1.getType(), cur1.getValue() + cur2.getValue());
    }

    @Override
    public Currency eval(Currency cur, Double dob) throws WrongCalculationOperator {
        throw new WrongCalculationOperator("Addition of currency and simple number is impossible.");
    }

    @Override
    public Currency eval(Double dob, Currency cur) throws WrongCalculationOperator {
        throw new WrongCalculationOperator("Addition of simple number and currency is impossible.");
    }
}
