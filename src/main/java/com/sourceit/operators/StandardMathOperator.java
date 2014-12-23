package com.sourceit.operators;

import com.sourceit.exceptions.WrongCalculationOperator;
import com.sourceit.models.Currency;

/**
 * Created by Vladislav on 20.12.2014.
 */
public interface StandardMathOperator {
    Currency eval(Currency cur1, Currency cur2) throws WrongCalculationOperator;
    Currency eval(Currency cur, Double dob) throws WrongCalculationOperator;
    Currency eval(Double dob, Currency cur) throws WrongCalculationOperator;
}
