package com.sourceit.calculation;

import com.sourceit.exceptions.WrongCalculationOperator;
import com.sourceit.models.Currency;
import com.sourceit.operators.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by bvl on 12/19/2014.
 */
public class ReversePolishNotation {
    static boolean isDelim(char c) { // тру если пробел
        return c == ' ';
    }
    static boolean isOperator(char c) { // возвращяем тру если один из символов ниже
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    static boolean isNotCurrency(char c) {
        return isOperator(c) || c ==  '(' || c == ')' || c == ' ';
    }
    static boolean isCurrency(String s) {
        String dollar = "toDollar";
        String euro = "toEuro";
        return s.contains(dollar) || s.contains(euro);
    }
    static int priority(char op) {
        switch (op) { // при + или - возврат 1, при * / 2 иначе -1
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
    static void processOperator(LinkedList<Object> st, char op) throws WrongCalculationOperator {
        Object r = st.removeLast(); // выдёргиваем из упорядоченного листа последний элемент
        Object l = st.removeLast(); // выдергиваем предпоследний элемент
        switch (op) { // выполняем действие между l и r в зависимости от оператора в кейсе и результат валим в st
            case '+':
                st.add(calculate(r, l, new Addition()));
                break;
            case '-':
                st.add(calculate(r, l ,new Subtraction()));
                break;
            case '*':
                st.add(calculate(r, l, new Multiplication()));
                break;
            case '/':
                st.add(calculate(r, l, new Division()));
                break;
        }
    }
    static Currency calculate(Object o1, Object o2, StandardMathOperator op) throws WrongCalculationOperator {
        if (o1 instanceof Currency && o2 instanceof Currency) {
            return op.eval((Currency) o1, (Currency) o2);
        } else if (o1 instanceof Double) {
            return op.eval((Double) o1, (Currency) o2);
        } else
        return op.eval((Currency) o1, (Double) o2);
    }
    public static int eval(String s) { // вводим выражение
        LinkedList<Object> st = new LinkedList<Object>(); // сюда наваливают цифры
        LinkedList<Character> op = new LinkedList<Character>(); // сюда опрераторы, и st и op в порядке поступления
        for (int i = 0; i < s.length(); i++) { // парсим строку с выражением и вычисляем
            char c = s.charAt(i);
            if (isDelim(c))
                continue;
            if (c == '(') // елси текущий елемент "(", то добавляем "(" в операторы
                op.add('(');
            else if (c == ')') { // если текущий елемент ")", то добавляем ")", если есть еще такие скобки
                while (op.getLast() != '(')
                    processOperator(st,op.removeLast()); // наполняем цифрами и операторами, у операторов удаляем последний в списке
                op.removeLast(); // удаляем скобку "(", массив операторов стал пустым
            } else if (isOperator(c)) { // если текущий елемент оператор (не скобка), то производим действия над числами (сначало * /, потом + -)
                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                    processOperator(st, op.removeLast());
                op.add(c);
            } else if (isCurrency(s)) {
                
            } else { // если текущий елемент число, или подряд идущие числа
                String value = "";
                String type = "";
                while (i < s.length() && isNotCurrency(s.charAt(i))){
                    if (s.charAt(i) == '.' || Character.isDigit(s.charAt(i))){
                        value += s.charAt(i++);
                    }else {
                        type += s.charAt(i++);
                    }
                }
                if (type.isEmpty()) {
                    Double operand = Double.parseDouble(value);
                    st.add(operand);
                } else {
                    Currency currency = new Currency(type, Double.parseDouble(value));
                    st.add(currency);
                }
                --i;
        }
    }
    while (!op.isEmpty())
        processOperator(st, op.removeLast());
    return st.get(0);  // возврат результата

    public static void main(String[] args) {
        System.out.println(eval("(3+2)*100+(10-1)"));
    }
}

