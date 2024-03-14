package org.project;

import java.util.ArrayList;

interface AppInformation {  // Интерфейс AppInformation
    void workerHello();  // Метод, который необходимо переопределить в классе Info

    void workerInfo();   // Метод, который необходимо переопределить в классе Info

    void workerHelp();  // Метод, который необходимо переопределить в классе Info
}

public class Info implements AppInformation {

    @Override
    public void workerHello() {           // Переопределённый метод workerHello выводит информацию о доступных действиях пользователя
        System.out.println("Добро пожаловать в приложение, которое управляет сотрудниками предприятия.\n");
        System.out.println("info - О приложении");
        System.out.println("show - Показать список сотрудников");
        System.out.println("help - Справка по командам");
        System.out.println("statistic - Статистика");
        System.out.println("exit - Выйти");

    }


    @Override
    public void workerInfo() {              // Переопределённый метод workerInfo выводит информацию о коллекции
        System.out.println("Приложение, worker-manager, позволяет вам решать минимальные задачи в управлении вашими сотрудниками предприятия.");
        System.out.println("Вам позволено добавлять и удалять сотрудников, Назначать зарплаты, сортировать по id, зарплате, полу и статусу.");
        System.out.println("Так же вы можете получить общую сумму всех зарплат и есть возможность удалить всех сотрудников.");
        System.out.println("Самое главное, что информация под защитой вашего пароля и никто, кроме вас, не может получить данные из этого приложения.");
        System.out.println("Подробней о командах можно прочитать в справке.\n");
        System.out.println("back - Вернуться\nindex - Главный activity");
        System.out.println("exit - выйти");
    }

    @Override
    public void workerHelp() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("info: вывести информацию о коллекции работников");
        commands.add("show: вывести все элементы коллекции работников");
        commands.add("mode new - добавить работника в коллекцию");
        commands.add("mode edit - редактирование работника");
        commands.add("sort id - сортировка работников по id");
        commands.add("sort salary - сортировка работников по зарплатам");
        commands.add("help - справка");
        commands.add("statistic - статистика приложения о работниках");
        commands.add("mode search - поиск работника");
        commands.add("mode clear - удалить всех работников");
        commands.add("mode delete - удалить работника по id");
        commands.add("yes - подтверждает действие");
        commands.add("no - отменяет действие");
        commands.add("stop - покинуть редактор информации");
        commands.add("exit - выход из приложения");
        commands.add("back - вернуться назад");
        commands.add("index - главный activity");

        for (String command : commands) {
            System.out.println(command);
        }
    }

}
