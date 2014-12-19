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
/*    public static void main(String[] args) throws IOException {
        BufferedReader readExpression = new BufferedReader(new InputStreamReader(System.in));
        String exp = readExpression.readLine();
        char[] expChar = exp.toCharArray();
        Stack stack = new Stack();
        LinkedList list = new LinkedList();
        if (exp.contains("0")) {
            String n1 = "0";
            list.add(n1);
        }

    }*/

    static boolean isDelim(char c) { // тру если пробел
        return c == ' ';
    }
    static boolean isOperator(char c) { // возвращяем тру если один из символов ниже
        return c == '+' || c == '-' || c == '*' || c == '/';
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
    static void processOperator(LinkedList<Integer> st, char op) {
        int r = st.removeLast(); // выдёргиваем из упорядоченного листа последний элемент
        int l = st.removeLast(); // также
        switch (op) { // выполняем действие между l и r в зависимости от оператора в кейсе и результат валим в st
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);
                break;
            case '*':
                st.add(l * r);
                break;
            case '/':
                st.add(l / r);
                break;
        }
    }
    public static int eval(String s) { // вводим выражение
        LinkedList<Integer> st = new LinkedList<Integer>(); // сюда наваливают цифры
        LinkedList<Character> op = new LinkedList<Character>(); // сюда опрераторы и st и op в порядке поступления
        for (int i = 0; i < s.length(); i++) { // парсим строку с выражением и вычисляем
            char c = s.charAt(i);
            if (isDelim(c))
                continue;
            if (c == '(')
                op.add('(');
            else if (c == ')') {
                while (op.getLast() != '(')
                    processOperator(st,op.removeLast());
                op.removeLast();
            } else if (isOperator(c)) {
                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                    processOperator(st, op.removeLast());
                op.add(c);
            } else {
                String operand = "";
                while (i < s.length() && Character.isDigit(s.charAt(i)))
                    operand += s.charAt(i++);
                --i;
                st.add(Integer.parseInt(operand));
            }
        }
        while (!op.isEmpty())
            processOperator(st, op.removeLast());
        return st.get(0);  // возврат результата
    }

    public static void main(String[] args) {
        System.out.println(eval("(3+2)*100+(10-1)"));
    }
}
