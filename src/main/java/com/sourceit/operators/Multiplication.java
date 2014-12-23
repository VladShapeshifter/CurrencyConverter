package com.sourceit.operators;

import com.sourceit.exceptions.WrongCalculationOperator;
import com.sourceit.models.Currency;

/**
 * Created by Vladislav on 20.12.2014.
 */
public class Multiplication implements StandardMathOperator {

    @Override
    public Currency eval(Currency cur1, Currency cur2) throws WrongCalculationOperator {
        throw new WrongCalculationOperator("Multiplication can't be done for currency.");
    }

    @Override
    public Currency eval(Currency cur, Double dob) {
        return new Currency(cur.getType(), cur.getValue() * dob);
    }

    @Override
    public Currency eval(Double dob, Currency cur) {
        return eval(cur, dob);
    }
}
