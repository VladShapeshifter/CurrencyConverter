package com.sourceit.determine;

import com.sourceit.models.Currency;

import java.io.IOException;

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
            stringArr = read.split("\\(");
            for (int i = 0; i < stringArr.length; i++) {
                String typeFromIn = "";
                if (stringArr[i].matches("\\D+")) {
                    typeTo = stringArr[i];
                }
                if (stringArr[i].contains(")")){
                    stringArr[i] = stringArr[i].substring(0, stringArr[i].length() - 1);
                }
                String valueS = "";
                int y = 0;
                if (stringArr[i].matches("(\\D+\\d+\\.\\d+)|(\\d+\\.\\d+\\D+)|(\\D+\\d+)|(\\d+\\D+)")) {
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
            userInput = null;
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

    /*public static void main(String[] args) throws IOException {
        DetermineRates determineRates = new DetermineRates();
        DetermineConversion determineConversion = new DetermineConversion();
        Currency currency = determineConversion.determine("toDollar(3euro)", determineRates);
        System.out.println(currency.getType() + " " + currency.getValue() + " " + currency.getRates());
    }*/
}
