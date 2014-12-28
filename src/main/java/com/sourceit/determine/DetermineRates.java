package com.sourceit.determine;

import com.sourceit.calculation.ReversePolishNotation;
import com.sourceit.models.Currency;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Created by Vladislav on 24.12.2014.
 */
public class DetermineRates {
    private double rates;
    private String fromCur;
    private String toCur;

    public DetermineRates(String fromCur, String toCur, double rates) {
        this.fromCur = fromCur;
        this.toCur = toCur;
        this.rates = rates;
    }
    public static void execute() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/currencyrates.txt"));
        String read;
        String[] stringArr;
        while ((read = reader.readLine())!=null) {
            stringArr = read.split(" ");
            String typeFrom = "";
            for (int i = 0; i < stringArr.length; i++) {
                if (stringArr[i].matches("\\D+")) {
                    typeFrom = stringArr[i];
                }
                String rateS = "";
                double rateD;
                String typeTo = "";
                int y = 0;
                if (stringArr[i].matches("(\\D+\\d+\\.\\d+)|(\\d+\\.\\d+\\D+)")) {
                    while (y < stringArr[i].length()/* && ReversePolishNotation.isNotCurrency(stringArr[i].charAt(y))*/) {
                        if (stringArr[i].charAt(y) == '.' || Character.isDigit(stringArr[i].charAt(y))) {
                            rateS += stringArr[i].charAt(y++);
                        } else {
                            if (stringArr[i].charAt(y) != ' ')
                            typeTo += stringArr[i].charAt(y++);
                        }
                    }
                    rateD = Double.parseDouble(rateS);

                    System.out.println("Type From: " + typeFrom + " " + "Type To: " + typeTo + " " + "RateD: " + rateD);
                }
            }
        }
        reader.close();
    }

    public double getRates() {
        return rates;
    }

    public void setRates(double rates) {
        this.rates = rates;
    }

    public String getFromCur() {
        return fromCur;
    }

    public void setFromCur(String fromCur) {
        this.fromCur = fromCur;
    }

    public String getToCur() {
        return toCur;
    }

    public void setToCur(String toCur) {
        this.toCur = toCur;
    }
}
