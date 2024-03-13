package org.project;

/**
 * Этот класс хранит некоторые методы, которые могут помочь в работе приложения.
 */
public class Tools {
    /**
     * Метод формирует правильное окончание слов для счетчика в русском языке.
     * Пример: 1 работник, 2 работника, 5 работников
     *
     * @param number - принимает число
     * @param punct  - знак разделения
     * @param str    - строка со словами
     *               Пример
     *               changeWord(5,",","работник,работника,работников")
     */
    public static String changeWord(int number, String punct, String str) {
        int n = 0;
        if (number < 100) {
            n = number;
        } else {
            String numberString = Integer.toString(number);
            int numberStringLength = numberString.length();
            n = Integer.parseInt(numberString.substring(numberStringLength - 1, numberStringLength));
        }
        String[] ex = str.split(punct);
        if (n == 1 || n == 21 || n == 31 || n == 41 || n == 51 || n == 61 || n == 71 || n == 81 || n == 91) {
            return ex[0];
        } else if (n == 2 || n == 3 || n == 4 || n == 22 || n == 23 || n == 24 || n == 32 || n == 33 || n == 34 || n == 42 || n == 43 || n == 44 || n == 52 || n == 53 || n == 54 || n == 62 || n == 63 || n == 64 || n == 72 || n == 73 || n == 74 || n == 82 || n == 83 || n == 84 || n == 92 || n == 93 || n == 94) {
            return ex[1];
        } else {
            return ex[2];
        }
    }




    /* Метод выводит адрес бар в консоль */
    public static void pathBar(String data) {
        if (data.length() > 0) {
            System.out.println("PATH: " + data);
        }
    }

    /* Метод, который может получить конкретное значение */
    /* Этот метод нужен для поиска модуля в адресе. */
    public static String module(String path, int n) {
        String[] s = path.split("/"); // превратили путь в массив строк
        String str = ""; // Строка, которая будет собирать результат из цикла

        for (int i = 1; i <= s.length - 1; i++) {
            if (i == n) {
                str += "/" + s[i];
                break;
            }
        }
        return str;
    }

    /* Метод, который вернет адрес для возврата. */
    public static String backPath(String path) {
        String[] s = path.split("/"); // превратили путь в массив строк
        String str = ""; // Строка, которая будет собирать результат из цикла

        for (int i = 1; i <= s.length - 2; i++) {
            str += "/" + s[i];
        }
        return str;
    }

    /* Метод, который формирует адрес к модулям */
    public static String redirect(String d, String path) {
        if (d.length() > 0) { // откроется,  когда не пустая строка
            // Возврат пользователя
            if ("/list/update".equals(d)) {
                return path;
            } else if ("index".equals(d)) {
                return "";
            } else if ("back".equals(d)) {
                return backPath(path);
            } else {
                return path + "/" + d;
            }
        }
        return path;
    }


}