package org.project;

interface AppInformation {  // Интерфейс AppInformation
    void workerHello();  // Метод, который необходимо переопределить в классе Info
    void workerInfo();   // Метод, который необходимо переопределить в классе Info
    void workerHelp();  // Метод, который необходимо переопределить в классе Info
}
public class Info implements AppInformation{

    @Override
    public void workerHello() {           // Переопределённый метод workerHello выводит информацию о доступных действиях пользователя
        System.out.println("Добро пожаловать в приложение, которое управляет сотрудниками предприятия.\n");
        System.out.println("info - О приложении");
        System.out.println("show - Показать список сотрудников");
        System.out.println("help - Справка по командам");
        System.out.println("statistic - Статистика");
        System.out.println("exit - Выйти");

    }

    /**
     * Этот метод нужно доделать.
     * Совет
     * В классе Tools Есть метод changeWord, который умеет изменять окончание слов.
     * Примените его к количеству работников, чтобы выводило примерно такое
     * 1 работник, 2 работника, 5 работников
     * Так же посчитайте сумму всех зарплат и выводите в этом методе.
     *
     */
    public void statistic() {
        System.out.println("Статистика: 5 "+Tools.changeWord(5,",","работник,работника,работников"));
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
    public void workerHelp() {                // Переопределённый метод workerHelp выводит информацию о доступных пользователю командах
        System.out.println("info: вывести информацию о коллекции работников");
        System.out.println("show: вывести все элементы коллекции работников");
        System.out.println("add: добавить новый элемент в коллекцию работников");
        System.out.println("update (id): обновить элемент коллекции работников");
        System.out.println("remove_by_id: удалить элемент по id");
        System.out.println("clear: очистить коллекцию работников");
        System.out.println("exit: завершить программу(без сохранения)");
        System.out.println("add_if_max: добавить новый элемент в коллекцию работников");
        System.out.println("remove_lower: удалить из коллекции работников все элементы");
        System.out.println("sum_of_salary: вывести сумму значений зарплат");
        System.out.println("filter_starts_with_name: вывести элементы, значение поля name");
        System.out.println("print_field_ascending_status: вывести значения поля status всех элементов в порядке возрастания");
    }
}
