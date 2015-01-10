package com.sourceit.operators;

import com.sourceit.determine.DetermineRates;
import com.sourceit.exceptions.WrongCalculationOperator;
import com.sourceit.models.Currency;

/**
 * Created by Vladislav on 03.01.2015.
 */
public interface MultiplicationToRates {
    Currency eval(Currency cur, Double rates) throws WrongCalculationOperator;
    Currency eval(Double dob, Double rates) throws WrongCalculationOperator;
}
