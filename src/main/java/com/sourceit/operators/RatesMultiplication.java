package com.sourceit.operators;

import com.sourceit.determine.DetermineRates;
import com.sourceit.exceptions.WrongCalculationOperator;
import com.sourceit.models.Currency;

/**
 * Created by Vladislav on 03.01.2015.
 */
public class RatesMultiplication implements MultiplicationToRates {

    @Override
    public Currency eval(Currency cur, Double rates) throws WrongCalculationOperator {
        Double result = cur.getValue() * rates;
        return new Currency(cur.getType(), result);
    }

    @Override
    public Currency eval(Double dob, Double rates) throws WrongCalculationOperator {
        throw new WrongCalculationOperator("Multiplication result of non currency can't be done.");
    }
}
