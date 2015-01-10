package com.sourceit.determine;

import com.sourceit.models.Currency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vladislav on 24.12.2014.
 */
public class DetermineConversion {
    private String read;
    private String[] stringArr;
    private String typeTo = "";
    private String typeFrom = "";
    private double valueD = 0.0;
    public Currency determine(String userInput, DetermineRates determineRates) throws IOException {
        while ((read = userInput)!=null) {
            stringArr = read.split(" ");
            for (int i = 0; i < stringArr.length; i++) {
                String typeFromIn = "";
                if (stringArr[i].matches("\\D+")) {
                    typeTo = stringArr[i];
                }
                String valueS = "";
                int y = 0;
                if (stringArr[i].matches("(\\D+\\d+\\.\\d+)|(\\d+\\.\\d+\\D+)")) {
                    while (y < stringArr[i].length()) {
                        if (stringArr[i].charAt(y) == '.' || Character.isDigit(stringArr[i].charAt(y))) {
                            valueS += stringArr[i].charAt(y++);
                        } else {
                            if (stringArr[i].charAt(y) != ' ')
                                typeFromIn += stringArr[i].charAt(y++);
                        }
                    }
                    typeFrom = typeFromIn;
                    valueD = Double.parseDouble(valueS);

                }
            }
        }
        Currency currency = new Currency(typeFrom, valueD);
        if (determineRates.getMap1().containsKey(typeTo)) {
            double d = determineRates.getMap2().get(typeFrom);
            currency.setRates(d);
        }
        return currency;
    }

    public String getTypeTo() {
        return typeTo;
    }

    public String getTypeFrom() {
        return typeFrom;
    }

    public double getValueD() {
        return valueD;
    }
}
