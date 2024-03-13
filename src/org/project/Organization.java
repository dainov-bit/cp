package org.project;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Set;

enum Status {
    DIRECTOR("Директор"),
    DEPUTY_DIRECTOR("Заместитель директора"),
    SECRETARY("Секретарь"),
    BOOKER("Бухгалтер"),
    DRIVER("Водитель"),
    DOCTOR("Доктор"),
    WORKER("Простой рабочий");
    private String title;

    Status(String title) {
        this.title = title;
    }

    public String get() {
        return title;
    }
}

public class Organization {
    // Храним наших работников
    private Set<Worker> worker = new HashSet<>();
    private int count = 1; // для назначения id новому работнику
    private String fullName; // Имя компании

    // Счетчик для id
    private int counter() {
        return this.count++;
    }

    Organization(String fullName) {
        this.worker.add(new Worker("Иванов Иван Иванович", 2200.05, Status.DIRECTOR, this.counter()));
        this.worker.add(new Worker("Петров Семен Александрович", 1955.05, Status.BOOKER, this.counter()));
        this.worker.add(new Worker("Петрова Наталья Ивановна", 2100.99, Status.DRIVER, this.counter()));
        this.worker.add(new Worker("Шевченко Регина Юрьевна", 3500.67, Status.WORKER, this.counter()));
        this.worker.add(new Worker("Шептун Лев Генадьевич", 2900.05, Status.DOCTOR, this.counter()));
        this.fullName = fullName;
    }

    /**
     * Метод выводит имя организации
     *
     * @return выводит строку
     */
    public String getName() {
        return this.fullName;
    }

    /**
     * Удаляем объект из HashSet
     *
     * @param obj Объект для удаления
     */
    public void drop(Worker obj) {
        this.worker.remove(obj);
    }

    /**
     * Метод, который выполняет очистку работников
     */
    public void clear() {
        /* Метод нужно реализовать */
    }

    /**
     * Метод, который добавляет нового пользователя в HashSet.
     * Он используется в классе App, внутри другого метода, который выполняет проверку данных
     *
     * @param name   Имя работника
     * @param salary Зарплата работника
     * @param status Статус работника
     */
    public void addWorker(String name, double salary, Status status) {
        this.worker.add(new Worker(name, salary, status, this.counter()));
    }

    /**
     * Этот метод выполняет заполнение ArrayList из HashSet
     * Это нужно для правильной сортировки данных
     *
     * @return Выводит ArrayList
     */
    private ArrayList hashSetToArrayList() {
        ArrayList<Worker> alw = new ArrayList<>();
        Iterator<Worker> i = this.worker.iterator();
        while (i.hasNext()) {
            alw.add(i.next());
        }
        return alw;
    }

    /**
     * Метод выводит список работников
     *
     * @param type  Тип. К примеру sort
     * @param query Указывает, как происходит сортировка. К примеру по id
     */
    public void listWorker(String type, String query) {
        Iterator<Worker> i; // Переменная для итератора
        ArrayList<Worker> alw; // переменная для ArrayList
        if (type == "sort" || type == "search") {
            if (query == "id") {
                alw = this.hashSetToArrayList();
                Collections.sort(alw, new WorkerComparator());
                i = alw.iterator();
                System.out.println("Отсортировано по id");
            } else if (query == "salary") {
                alw = this.hashSetToArrayList();
                Collections.sort(alw);
                i = alw.iterator();
                System.out.println("Отсортировано по зарплате\n");
            } else {
                i = this.worker.iterator();
            }
        } else {
            i = this.worker.iterator();
        }
        while (i.hasNext()) {
            Worker w = i.next();
            System.out.println("Работник: " + w.getName() + "\nДолжность: " + w.getStatus() + "\nЗарплата: " + w.getSalary() + "\nID: " + w.getId() + "\n");
        }
    }

    /**
     * Следующий метод передает итератор HashSet для класса App, чтобы там можно было пройти по коллекции
     *
     * @return Возвращает Iterator
     */
    public Iterator idIterator() {
        return this.worker.iterator();
    }

}


