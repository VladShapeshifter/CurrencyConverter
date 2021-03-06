package com.sourceit.determine;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vladislav on 24.12.2014.
 */
public class DetermineRates {
    private String read;
    private String[] stringArr;
    private static String typeTo = "";
    private static String typeFrom = "";
    private double rateD = 0.0;
    private Map map1 = new HashMap<>();
    private Map<String, Double> map2 = new HashMap<>();
    public DetermineRates() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/currencyRates.txt"));
        while ((read = reader.readLine())!=null) {
            stringArr = read.split(" ");
            for (int i = 0; i < stringArr.length; i++) {
                String typeFromIn = "";
                if (stringArr[i].matches("\\D+")) {
                    typeTo = stringArr[i];
                }
                String rateS = "";
                int y = 0;
                if (stringArr[i].matches("(\\D+\\d+\\.\\d+)|(\\d+\\.\\d+\\D+)")) {
                    while (y < stringArr[i].length()) {
                        if (stringArr[i].charAt(y) == '.' || Character.isDigit(stringArr[i].charAt(y))) {
                            rateS += stringArr[i].charAt(y++);
                        } else {
                            if (stringArr[i].charAt(y) != ' ')
                                typeFromIn += stringArr[i].charAt(y++);
                        }
                    }
                    typeFrom = typeFromIn;
                    rateD = Double.parseDouble(rateS);
                    map1.put(typeTo, map2.put(typeFrom, rateD));
//                    System.out.println("Type To: " + typeTo + " " + "Type From: " + typeFrom + " " + "RateD: " + rateD);
                }
            }
        }
        reader.close();
    }

    public String getTypeTo() {
        return typeTo;
    }

    public String getTypeFrom() {
        return typeFrom;
    }

    public double getRateD() {
        return rateD;
    }

    public Map getMap1() {
        return map1;
    }

    public Map<String, Double> getMap2() {
        return map2;
    }

    /*public static void main(String[] args) throws IOException {
        DetermineRates determineRates = new DetermineRates();
        System.out.println(typeTo);
        System.out.println(typeFrom);
        if (determineRates.getMap1().containsKey(typeTo)) {
            double d = determineRates.getMap2().get(typeFrom);
            System.out.println(d);
        }
    }*/
}
