package unpacker;

import java.util.Stack;
import java.util.regex.Pattern;

public class Unpacker {

    private String inpString;
    private String outString;

    public Unpacker(String inpString) {

        Stack<Character> brackets = new Stack<>();
        for (Character symbol : inpString.toCharArray()) {
            if (symbol == '[') {
                brackets.push(symbol);
            }
            if (symbol == ']') {
                if (brackets.isEmpty()) {
                    brackets.push(']');
                    break;
                } else
                    brackets.pop();
            }
        }


        if (Pattern.matches(".*[^a-zA-Z0-9\\[\\]].*", inpString)) {
            throw new IllegalArgumentException("Введен недопустимый символ");
        }
        if (!brackets.isEmpty()) {
            throw new IllegalArgumentException("Неверная расстановка скобок");
        }
        if (Pattern.matches(".*[^\\d]\\Q[\\E.*", inpString)) {
            throw new IllegalArgumentException("Перед скобкой должно стоять число");
        }
        if (Pattern.matches(".*[0-9]([a-zA-Z]|\\Q]\\E).*|.*[0-9]", inpString)) {
            throw new IllegalArgumentException("Число может стоять только перед открывающей скобкой");
        }
        if (Pattern.matches(".*\\Q[]\\E.*", inpString)) {
            throw new IllegalArgumentException("Пустые скобки");
        }
        if (Pattern.matches(".*[^\\d]\\Q[\\E.*|\\[.*", inpString)) {
            throw new IllegalArgumentException("Перед скобкой должно стоять число");
        }

        this.inpString = inpString;
        this.outString = inpString;

    }

    public String unpack() {


        /**
         * Цикл обходит посимвольно с конца поступившую строку
         * скобки заносятся в стек скобок
         * подстроки заносятся в стек подстрок, в стек скобок заносится символ "*", помечающий наличие строки между скобок
         * после обработки числа, отвечающего за пару скобок, вытаскивается нужная подстрока
         * и повторяется определенное количество раз
         * из стека удаляется группа элементов вида [*]
         * Далее происходит проверка на одноуровневость текущей и предыдущей подстроки
         * если в стеке идут подряд **, значит строки нужно склеить в одну и положить обратно в стек скобок
         * если нет, то сразу кладем подстроку в стек
         */


        Stack<Character> brackets = new Stack<>();
        Stack<String> substrings = new Stack<>();
        String number = "";
        String substring = "";

        String inpStringReverce = new StringBuilder(inpString).reverse().toString();
        for (Character symbol : inpStringReverce.toCharArray()) {

            if (symbol.equals(']')) {

                if (!substring.isEmpty()) {
                    if (!brackets.isEmpty() && brackets.peek().equals('*')) {
                        substring = substring + substrings.pop();
                        substrings.push(substring);
                        substring = "";
                    } else {
                        substrings.push(substring);
                        substring = "";
                        brackets.push('*');
                    }
                }

                if (!number.isEmpty()) {

                    brackets.pop();
                    brackets.pop();
                    brackets.pop();

                    substring = new String(new char[Integer.parseInt(number)]).replace("\0", substrings.pop());
                    number = "";

                    if (!brackets.isEmpty() && brackets.peek().equals('*')) {
                        substring = substring + substrings.pop();
                        substrings.push(substring);
                        substring = "";
                    } else {
                        substrings.push(substring);
                        substring = "";
                        brackets.push('*');
                    }

                }

                brackets.push(symbol);
            }
            if (symbol.equals('[')) {

                if (!substring.isEmpty()) {
                    if (!brackets.isEmpty() && brackets.peek().equals('*')) {
                        substring = substring + substrings.pop();
                        substrings.push(substring);
                        substring = "";
                    } else {
                        substrings.push(substring);
                        substring = "";
                        brackets.push('*');
                    }
                }

                if (!number.isEmpty()) {

                    brackets.pop();
                    brackets.pop();
                    brackets.pop();

                    substring = new String(new char[Integer.parseInt(number)]).replace("\0", substrings.pop());
                    number = "";

                    if (!brackets.isEmpty() && brackets.peek().equals('*')) {
                        substring = substring + substrings.pop();
                        substrings.push(substring);
                        substring = "";
                    } else {
                        substrings.push(substring);
                        substring = "";
                        brackets.push('*');
                    }

                }
                brackets.push(symbol);
            }
            if (Character.isDigit(symbol)) {
                number = symbol + number;
            }
            if (Character.isAlphabetic(symbol)) {

                if (!number.isEmpty()) {

                    brackets.pop();
                    brackets.pop();
                    brackets.pop();

                    substring = new String(new char[Integer.parseInt(number)]).replace("\0", substrings.pop());
                    number = "";

                    if (!brackets.isEmpty() && brackets.peek().equals('*')) {
                        substring = substring + substrings.pop();
                        substrings.push(substring);
                        substring = "";
                    } else {
                        substrings.push(substring);
                        substring = "";
                        brackets.push('*');
                    }

                }
                substring = symbol + substring;
            }
        }

        if (!number.isEmpty()) {

            brackets.pop();
            brackets.pop();
            brackets.pop();

            substring = new String(new char[Integer.parseInt(number)]).replace("\0", substrings.pop());
            number = "";

            if (!brackets.isEmpty() && brackets.peek().equals('*')) {
                substring = substring + substrings.pop();
                substrings.push(substring);
                substring = "";
            } else {
                substrings.push(substring);
                substring = "";
                brackets.push('*');
            }

        }

        if (!substring.isEmpty()) {
            if (!brackets.isEmpty() && brackets.peek().equals('*')) {
                substring = substring + substrings.pop();
                substrings.push(substring);
                substring = "";
            } else {
                substrings.push(substring);
                substring = "";
                brackets.push('*');
            }
        }

        outString = "";

        while (!substrings.empty()) {
            outString = outString + substrings.pop();
        }

        return outString;
    }
}
