package org.project;

import java.util.Iterator;
import java.util.Scanner;

/**
 * В этом классе выполняется основная работа приложения.
 * Методы, данного класса, берут на себя функциональность, вызывая вспомогательные классы
 */
public class App {
    /* Ниже будут статические поля, которые будут принимать динамические данные.
    Это нужно для полного функционирования приложения. */


    private static String data = ""; // поле, которое будет постоянно принимать команды с клавиатуры.
    private static String path = ""; // Поле, которое будет принимать путь к методу.
    private static Scanner scanner = new Scanner(System.in, "utf-8"); // Сканер с кодировкой utf-8.
    private static String login = ""; // Переменная хранит логин авторизации
    private static String system_login = "Admin"; // Логин, который хранит программа и будет делать проверку, верный ли логин из поля login
    private static String sort_type = ""; // Тип действия. К примеру sort или mode. Более подробней в методах, где это используется.
    private static String sort_query = ""; // Указываем тип действия или тип сортировки

    /* Далее поля, которые будут принимать временные данные для создания или редактирования пользователя.
    Мы не можем резковать и записывать данные на прямую в класс Worker.
    Для этого и существуют поля ниже, в которые будут заноситься данные, проверяться, а потом вноситься в класс Worker */

    private static String worker_name = ""; // Временно храним имя сотрудника
    private static double worker_salary = 0; // Времено храним зарплату сотрудника
    private static Status worker_status = null; // Временно храним должность сотрудника из enum
    private static String worker_error = ""; // Временно храним информацию про ошибку.
    private static Worker worker_obj = null; // Храним объект класса worker, в который будет помещен сотрудник.

    /* Дальше классы от Даниила и Дарьи
     */
    private static Organization org = new Organization("DomDom GmbH"); // Класс от Даниила
    private static Info info = new Info(); // Класс от Дарьи.

    /**
     * Следующий метод останавливает работу методов редактирования, добавления, удаления работника.
     * Его задача уничтожить данные из полей, которые принемают временные данные.
     */
    private static void stopEvent() {
        path = "";
        data = "";
        sort_type = "";
        sort_query = "";
        worker_status = null;
        worker_obj = null;
        worker_name = "";
        worker_salary = 0;
    }

    /**
     * Следующий метод удаляет одного работника из коллекции
     * Однако, это только информационный метод и в нем нет действий
     */
    private static void deleteWorker() {
        // На всякий случай делаем защиту, чтоб метод не сробатывал в других режимах
        if ("/delete_worker".equals(path) && "mode".equals(sort_type) && "delete_worker".equals(sort_query)) {
            // Выводим информацию про ошибку
            if (worker_error.length() != 0) {
                System.out.println(worker_error);
                worker_error = "";
            }

/* Для начала нужно найти работника.
Проверяем, пустое ли поле worker_obj, которое хранит объект класса Worker.
Если пустое, тогда предлагаем ввести id работника. */

            if (worker_obj == null) {
                System.out.println("Введите id пользователя, которого хотите удалить.\n");
            } else {
                /* Пользователь найден. Теперь предлагаем подтвердить удаление или отменить. Если пользователя нет, появится сообщение с ошибкой о том, что пользователя нет. */
                System.out.println("Отлично! Работник найден.\nЭто " + worker_obj.getName() + "\n\nДля удаления введите yes.\nДля отмены введите no\n");
            }
        }
    }

    /**
     * Следующий метод не информационный. Он полностью удаляет одного пользователя из колекции.
     * Обратите внимание на то, что внутри этого метода применяется метод stopEvent, который может прекратить удаление пользователя, покинув редактор и вернувшись в главный activity
     */
    private static void dropWorker() {
        // На всякий случай защита, чтобы метод не выполнился в других режимах.
        if ("/delete_worker".equals(path) && "mode".equals(sort_type) && "delete_worker".equals(sort_query)) {
            // Пользователь ввел команду stop. Теперь нужно остановить процесс удаления и выгнать пользователя в главный activity
            if ("stop".equals(data)) {
                stopEvent();
            }
            // Проверяем, пуст ли worker_obj, который хранит объект работника
            if (worker_obj == null) {
                // Если пуст, тогда нужно принять номер работника и обработать его, добавив работника в worker_obj
                try {
                    int id = Integer.parseInt(data);
                    // Так как в HashSet данные находятся в безпорядке, пройдемся итераторам по данным и найдем работникам
                    Iterator<Worker> i = org.idIterator();
                    while (i.hasNext()) {
                        Worker t = i.next();
                        if (t.getId() == id) {
                            // Пользователь найден и добавлен в worker_obj
                            worker_obj = t;
                        }
                    }
                    // Если работник не найден, тогда нужно сформировать ошибку.
                    if (worker_obj == null) {
                        worker_error = "Работника, с id " + id + ", нет в базе";
                    }
                } catch (Exception e) {
                    // Ошибка, пользователь ввел запрещенные символы
                    worker_error = "Ошибка! id должен состоять только из цифр";
                }
                data = ""; // Обязательная очистка этого поля, чтобы не было ошибок в приложении
            } else {
                // Ранее работник был найден и следующим шагом будет удаление его или отмена.
                if ("yes".equals(data)) {
                    // Работник удаляется, так как от пользователя пришла команда yes
                    org.drop(worker_obj);
                    stopEvent(); // В данном случае  этот метод выполняет очистку временных полей.
                    path = "/show"; // перебрасываем пользователя в список работников, чтобы проверить, действительно ли он удален
                    sort_type = "sort";
                    sort_query = "id"; // отсортировали по id
                } else if ("no".equals(data)) {
                    // пользователь ввел команду отмены и отменил удаление. Теперь выгоняем его в главный activity
                    stopEvent();
                }
            }
        }
    }

    /**
     * Следующий метод выводит список профессий из enum
     */
    private static void listStatus() {
        System.out.println("Введите номер должности\n ");
        int i = -1; // Этот счетчик нужен для указания номера профессии. Отсчет начинается с нуля. Если тут оставить ноль, то в цикле счет начнется с единицы. По этой причине счетчик начинается с -1
        for (Status f : Status.values()) {
            i++;
            System.out.println(i + " - " + f.get());
        }
    }

    /**
     * Следующий метод назначает профессию работнику
     * В его задачу входит назначение, а так же проверка на существование профессии
     *
     * @param n принимает int число
     * @return выводит true или false, проще говоря, boolean значение
     */
    private static boolean setStatusAddWorker(int n) {
        int i = -1;
        for (Status f : Status.values()) {
            i++;
            if (i == n) {
                worker_status = f; // профессия найдена и передана в worker_status
                return true;
            }
        }
        // Если профессия не найдена, тогда формируем ошибку
        worker_error = "Ошибка! Неверно выбрана профессия";
        return false;
    }

    /**
     * Следующий метод выводит информацию для редактирования работника
     */
    private static void editWorker() {
        // На всякий случай делаем защиту, чтобы этот метод не работал в других режимах
        if ("/edit_worker".equals(path) && "mode".equals(sort_type) && "edit".equals(sort_query)) {
            // Выводим ошибку, если что-то не так
            if (worker_error.length() != 0) {
                System.out.println(worker_error);
                worker_error = "";
            }
            System.out.println("Редактор пользователя");
            // Проверка, есть ли уже объект работника или нет
            if (worker_obj == null) {
                // Работника еще нет, предлагаем пользователю ввести id работника
                System.out.println("Введите id работника\nЕсли вы не знаете id, найдите работника среди других работников и посмотрите его id.\n");
            } else if (worker_name.length() == 0) {
                // Если имя для работника еще пустое, тогда просим пользователя ввести его
                System.out.println("Отлично! Работник найден.\n" + worker_obj.getName() + "\n\nПожалуйста введите новое имя.\nЕсли имя не нужно изменять, просто оставьте строку пустой.\n");
            } else if (worker_status == null) {
                // Тоже самое для статуса
                System.out.println("Супер! У работника имя " + worker_name + "\nТеперь нужно указать должность.\nПожалуйста введите номер должности из списка ниже.\nЕсли должность нужно сохранить, оставьте строку пустой и нажмите enter.\n");
                listStatus(); // Выводим список профессий
            } else if (worker_salary == 0) {
                // Тоже самое для зарплат
                System.out.println("И так, работник " + worker_name + ", теперь " + worker_status.get() + "\nДавайте назначим зарплату. Сейчас " + worker_obj.getSalary() + "\nВведите новую сумму. Если вы хотите сохранить данный результат, просто оставьте строку пустой и нажмите enter");
            } else {
                // Итог проверки и предлагаем сохранить работника
                System.out.println("Очень прекрасно!\nВы отредактировали профиль вашего работника.\nДавайте проверим информацию.\n\nИмя: " + worker_name + "\nДолжность: " + worker_status.get() + "\nЗарплата: " + worker_salary + "\n\nЕсли информация верна, тогда введите yes\nЕсли есть ошибки, введите no и вернитесь в редактор заново.");
            }
        }
    }

    /**
     * Следующий метод выполняет редактирование работника и не является информационным
     */
    private static void setEditWorker() {
        // на всякий случай проверка, чтобы метод не работал в других режимах
        if ("/edit_worker".equals(path)) {
            // Если пользователь ввел stop, тогда останавливаем редактирование и выбрасываем его в главный activity
            if ("stop".equals(data)) {
                stopEvent();
            }
            // Проверяем, есть ли уже работник в worker_obj или нет
            if (worker_obj == null) {
                // работника еще нет, тогда начинаем его искать по id
                try {
                    int id = Integer.parseInt(data);
                    Iterator<Worker> i = org.idIterator();
                    int x = 0;
                    while (i.hasNext()) {
                        x++;
                        Worker t = i.next();
                        if (t.getId() == id) {
                            worker_obj = t;
                        }
                    }
                    if (worker_obj == null) {
                        worker_error = "Работника, с id " + id + ", нет в базе";
                    }
                } catch (Exception e) {
                    worker_error = "При введение id нужно использовать только лишь цифры";
                    data = "";
                }
            } else if (worker_name.length() == 0) {
                // Проверяем имя на пустоту. Если пользователь присылает пустую строку, тогда в worker_name помещаем имя, которое работник уже имеет. Если строка приходит не пустой, тогда и назначаем ее как новое имя.
                if (data.length() == 0) {
                    worker_name = worker_obj.getName();
                } else {
                    worker_name = data;
                }
                data = "";
            } else if (worker_status == null) {
                // Для статуса все тоже самое, что и для имене.
                if (data.length() == 0) {
                    worker_status = worker_obj.getStatusForEdit();
                } else {
                    setStatusAddWorker(Integer.parseInt(data));
                }
                data = "";
            } else if (worker_salary == 0) {
                // Все тоже самое
                if (data.length() == 0) {
                    worker_salary = worker_obj.getSalary();
                } else {
                    try {
                        worker_salary = Double.parseDouble(data);
                    } catch (Exception e) {
                        worker_error = "Зарплата может быть только десятичным числом, ну или целым";
                    }
                }
                data = "";
            } else {
                // Данные на работника заполнены, теперь ждем команду на подтверждение или отмену редактирования
                if ("yes".equals(data)) {
                    // Сохраняем работника
                    worker_obj.set(worker_name, worker_status, worker_salary);
                    stopEvent();
                    path = "/show";
                } else if ("no".equals(data)) {
                    // Не сохраняем работника
                    stopEvent();
                } else {
                    data = "";
                }
            }
        }
    }

    /**
     * Следующий метод информационный и он преглашает пользователя ввести данные на нового работника
     */
    private static void addWorker() {
        if ("/new_worker".equals(path)) {
            // Выводим ошибку, если он есть
            if (worker_error.length() != 0) {
                System.out.println(worker_error);
                worker_error = "";
            }

            System.out.println("Регистрация нового работника\n");
            if (worker_name.length() == 0) {
                System.out.println("Пожалуйста, введите полное имя нового работника\n");
            } else if (worker_status == null) {
                System.out.println("Отлично! Нашего работника звать " + worker_name + "\nДавайте назначим ему должность\n");
                listStatus();
            } else if (worker_salary == 0) {
                System.out.println("Супер!\nРаботник, с именем " + worker_name + ", получает должность " + worker_status.get() + "\nДавайте назначим ему зарплату");
            } else {
                System.out.println("Почти все готово для регистрации нового работника.\nОсталось лишь проверить, верна ли информация.\n\nИмя: " + worker_name + "\nДолжность: " + worker_status.get() + "\nЗарплата: " + worker_salary + "\n");
                System.out.println("Если информация верна, вы можете ввести yes для сохранения.\nЕсли есть ошибки, тогда начните заново вводить информацию.\n");
            }
        }
    }

    /**
     * Следующий метод сохраняет нового пользователя
     */
    private static void setAddWorker() {
        if ("/new_worker".equals(path)) {
            // Пришла команда stop
            if ("stop".equals(data)) {
                stopEvent();
            }
            if (worker_name.length() == 0) {
                worker_name = data;
                if (data.length() == 0) {
                    worker_error = "Внимание! Имя не может быть пустым";
                }
                data = "";
            } else if (worker_status == null) {
                setStatusAddWorker(Integer.parseInt(data));
                data = "";
            } else if (worker_salary == 0) {
                try {
                    worker_salary = Double.parseDouble(data);
                } catch (Exception e) {
                    worker_error = "В зарплате должны быть только цифры и точка. Все остальное нельзя вводить";
                }
                data = "";
            } else {
                if ("yes".equals(data)) {
                    org.addWorker(worker_name, worker_salary, worker_status);
                    stopEvent();
                    path = "/show";
                } else if ("no".equals(data)) {
                    stopEvent();
                }
            }
        }
    }

    /**
     * Следующий метод решает задачу с адресацией.
     */
    private static void redirectPath() {
        if (data.length() > 0 & !"/new_worker".equals(path) & !"/edit_worker".equals(path)) { // откроется,  когда не пустая строка
            // Возврат пользователя
            if ("index".equals(data)) {
                path = "";
            } else if ("back".equals(data)) {
                path = Tools.backPath(path);
            } else {
                path = path + "/" + data;
            }
        }

    }

    /**
     * Метод, который формирует команды
     */
    private static void redirect() {
        // Проверяем, если пользователь ввел два слова.
        String[] dSplit = data.split(" ");
        if (dSplit.length > 1 && dSplit.length < 3) {
            // Далее проверяем, есть ли в этих словах специальные команды
            if ("sort".equals(dSplit[0]) && "id".equals(dSplit[1])) {
                // указываем сортировку по id
                sort_type = "sort";
                sort_query = "id";
                path = "/show";
            } else if ("sort".equals(dSplit[0]) && "salary".equals(dSplit[1])) {
                // указываем сортировку по зарплате
                sort_type = "sort";
                sort_query = "salary";
                path = "/show";
            } else if ("mode".equals(dSplit[0]) && "new".equals(dSplit[1])) {
                // активируем режим нового работника
                sort_type = "mode";
                sort_query = "new";
                path = "/new_worker";
                data = "";
            } else if ("mode".equals(dSplit[0]) && "edit".equals(dSplit[1])) {
                // включаем режим редактирования пользователя
                path = "/edit_worker";
                sort_type = "mode";
                sort_query = "edit";
                data = "";
            } else if ("mode".equals(dSplit[0]) && "delete".equals(dSplit[1])) {
                // активируем activity для удаления пользователя
                sort_type = "mode";
                sort_query = "delete_worker";
                path = "/delete_worker";
                data = "";
            } else if ("mode".equals(dSplit[0]) && "clear".equals(dSplit[1])) {
                sort_type = "mode";
                sort_query = "clear";
                data = "";
                path = "/clear";
            } else {
                // Если слова не подходят под каманды, тогда выполняется метод адресации
                redirectPath();
            }
        } else {
            redirectPath();
        }
    }

    /**
     * Метод, который дает возможность удалить всех работников.
     */
    private static void clearWorker() {
    /* Тут нужно реализовать логику очистки пользователей.
    Советую подсмотреть метод deleteWorker и dropWorker
    Что-то подобное нужно сделать тут.
    Так же есть метод в классе organization,
    org.clear();
    Его тоже нужно реализовать.
    Советую в классе Organization посмотреть метод drop.
    Он даст подсказку как удалить всех работников.
     */
        System.out.println("Метод сработал на команду mode clear");
    }

    /**
     * Метод, который является пусковым в приложении
     * В нем принимается решение, какой метод вызвать, какой адрес запустить
     */
    public static void run() {
        /* Далее идет цикл do while, в котором и будем обрабатывать команды. */
        do {
            // Проверим, авторизирован ли пользователь или нет
            if (login.length() > 0 && login.equals(system_login)) {
                String module = Tools.module(path, 1); // Находим адрес первого уровня
                Tools.pathBar(path);

                switch (module) {
                    case "/clear":
                        clearWorker();
                        break;
                    case "/statistic":
                        info.statistic();
                        break;
                    case "/delete_worker":
                        deleteWorker();
                        break;
                    case "/edit_worker":
                        editWorker();
                        break;
                    case "/new_worker":
                        addWorker();
                        break;
                    case "/show":
                        org.listWorker(sort_type, sort_query);
                        break;
                    case "/info":
                        info.workerInfo();
                        break;
                    case "/help":
                        info.workerHelp();
                        break;
                    default:
                        info.workerHello();
                }


                data = scanner.nextLine().toLowerCase();
                setAddWorker(); // Слушаем событие добавления работника
                setEditWorker(); // Слушаем событие редактирования работника
                dropWorker(); // слушаем удаление работника
                redirect(); // Адресация и назначение команд

            } else {
                System.out.println("Чтобы войти в систему учета, нужно ввести пароль.\nПодсказка: Пароль Admin\n");
                data = scanner.nextLine();
                login = data;
            }

        } while (!"exit".equals(data));
        System.exit(0);
    }
}
