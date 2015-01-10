package com.sourceit.calculation;

import com.sourceit.determine.DetermineConversion;
import com.sourceit.determine.DetermineRates;
import com.sourceit.exceptions.WrongCalculationOperator;
import com.sourceit.models.Currency;
import com.sourceit.operators.*;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by bvl on 12/19/2014.
 */
public class ReversePolishNotation {
    static String toCurrency = "";
    static String userInput;
    static DetermineRates determineRates;
    static boolean isDelim(char c) { // тру если пробел
        return c == ' ';
    }
    static boolean isOperator(String c) throws IOException { // возвращяем тру если один из символов ниже
        if (isConversion()) {
            return c == toCurrency;
        }
        return c == "+" || c == "-" || c == "*" || c == "/";
    }
    public static boolean isNotCurrency(String c) throws IOException {
        return isOperator(c) || c ==  "(" || c == ")" || c == " ";
    }
    private static boolean isConversion() throws IOException {
        determineRates = new DetermineRates();
        return determineRates.getMap1().containsKey(toCurrency);
    }
    static int priority(String op) {
        Map switchMap = new HashMap();
        switchMap.put("+", 1);
        switchMap.put("-", 1);
        switchMap.put("*", 2);
        switchMap.put("/", 2);
        switchMap.put(toCurrency, 3);
        return ((int)switchMap.get(op));
    }
    static void processOperator(LinkedList<Object> st, String op) throws WrongCalculationOperator, IOException {
        Object r = st.removeLast(); // выдёргиваем из упорядоченного листа последний элемент
        Object l = st.removeLast(); // выдергиваем предпоследний элемент
        if (op == "+") st.add(calculate(r, l, new Addition()));
        if (op == "-") st.add(calculate(r, l ,new Subtraction()));
        if (op == "*") st.add(calculate(r, l, new Multiplication()));
        if (op == "/") st.add(calculate(r, l, new Division()));
        if (op == toCurrency) st.add(multiplyToRates(r, new RatesMultiplication()));

    }
    static Currency calculate(Object o1, Object o2, StandardMathOperator op) throws WrongCalculationOperator {
        if (o1 instanceof Currency && o2 instanceof Currency) {
            return op.eval((Currency) o1, (Currency) o2);
        } else if (o1 instanceof Double) {
            return op.eval((Double) o1, (Currency) o2);
        } else
        return op.eval((Currency) o1, (Double) o2);
    }
    static Currency multiplyToRates(Object object, MultiplicationToRates op) throws IOException, WrongCalculationOperator {
        DetermineConversion determineConversion = new DetermineConversion();
        Currency currency = determineConversion.determine(userInput, determineRates);
        Double rates = currency.getRates();
        if (object instanceof Currency) {
            return op.eval((Currency)object, rates);
        } else
            return op.eval((Double) object, rates);
    }

    public static Object eval(String s) throws WrongCalculationOperator, IOException { // вводим выражение
        LinkedList<Object> st = new LinkedList<Object>(); // сюда наваливают цифры
        LinkedList<String> op = new LinkedList<String>(); // сюда опрераторы, и st и op в порядке поступления
        userInput = s;
        for (int i = 0; i < s.length(); i++) { // парсим строку с выражением и вычисляем
            char c = s.charAt(i);
            String str = Character.toString(c);
            if (isDelim(c)) // каждый последующим символ всегда проверяется, не пробел ли он
                continue;
            if (Character.isAlphabetic(c)) {
                toCurrency += c;
                str = toCurrency;
            }
            if (isOperator(str)) {
                while (!op.isEmpty() && priority(op.getLast()) >= priority(str))// иначе если символ = (+-*/), то пока в op есть
                    // операторы, выполнять действия надчислами согласно приоритету действия, одновременно передаем и удаляем последний оператор из op
                    processOperator(st, op.removeLast());
                op.add(str); // добавить +-*/
            } else if (c == '(')
                op.add("("); // иначе елси символ = "(", то добавляем "(" в операторы
            else if (c == ')') {
                while (op.getLast() != "(")
                    processOperator(st, op.removeLast()); // иначе если символ = ")", то, пока последний символ в op не "(", добавляем результат сложения
                    // (+ - * /) над последним и предпоследним числами в st, одновременно передаем и удаляем последний оператор из op
                    op.removeLast(); // удалить "("
            } else { // иначе, т.е. если это цифры или буквы или $, то добавить их в st
                String value = "";
                String type = "";
                while (i < s.length() && !isNotCurrency(Character.toString(s.charAt(i)))) {
                    if (s.charAt(i) == '.' || Character.isDigit(s.charAt(i))) {
                        value += s.charAt(i++);
                    } else {
                        type += s.charAt(i++);
                    }
                }
                if (type.isEmpty()) {
                    Double operand = Double.parseDouble(value);
                    st.add(operand); // добавить простое число Double, вместо Currency с валютой и значением
                } else {
                    Currency currency = new Currency(type, Double.parseDouble(value));
                    st.add(currency); // добавить Currency валюту и значение в st
                }
                --i;
            }
        }
        while (!op.isEmpty())
            processOperator(st, op.removeLast());
        return (Currency)st.get(0);  // возврат результата
    }




    public static void main(String[] args) throws WrongCalculationOperator, IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        String userInput = reader.readLine();
//        System.out.println(eval("toDollar(3euro)"));
        Currency res = (Currency)eval("toDollar(3euro)");
        System.out.println(res.getValue() + " " + res.getType());
//        System.out.println(eval(userInput));
    }
}

