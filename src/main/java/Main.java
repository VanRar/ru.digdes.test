import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the line types number[string]");
        String inputStr = scanner.nextLine();
        if (isValidity(inputStr)) {
            System.out.println(convertLine(inputStr));
        } else {
            System.out.println("the string is not valid");
        }
    }

    public static boolean isValidity(String inputStr) {
        //лучше конечно возвращать true or false

        //считаем количество открытых и закрытых скобок
        int count = 0;
        for (char ch : inputStr.toCharArray())
            if (ch == '[') count++;
            else if (ch == ']') count--;
        // проверяем все ли скобки закрыты и введены ли допустимые символы
        return count == 0 && !inputStr.matches(".*[^a-zA-Z0-9\\[\\]].*");
    }

    public static String convertLine(String inputStr) {
        StringBuilder result = new StringBuilder(); //StringBuilder в который будем записывать результат

        int multiplier = 1; // множитель для строки в скобках
        int firstIndexBracket = 0; // индекс открывающейся скобки
        int countBracket = 0; //счётчик скобок

        for (int i = 0; i < inputStr.length(); i++) {
            //записываем значения символов до скобок
            if (Character.isLetter(inputStr.charAt(i)) && countBracket == 0) {
                result.append(inputStr.charAt(i));
            }
            //если символ - число до скобок, то это множитель строки, считаем что множитель - однозначное число.
            if (Character.isDigit(inputStr.charAt(i)) && countBracket == 0) {
                multiplier = Integer.parseInt(String.valueOf(inputStr.charAt(i)));
            }
            //ищем начало подстроки
            if (inputStr.charAt(i) == '[') {
                countBracket++;
                if (countBracket == 1) {
                    firstIndexBracket = i;
                }
            }
            //ищем конец подстроки
            if (inputStr.charAt(i) == ']') {
                countBracket--;
                if (countBracket == 0) {

                    //к сожалению рекурсия
                    result.append(convertLine(inputStr.substring(firstIndexBracket + 1, i)).repeat(multiplier));

                    //обнуляем показатели
                    firstIndexBracket = 0;
                    multiplier = 1;
                }
            }
        }
        return result.toString();
    }
}
