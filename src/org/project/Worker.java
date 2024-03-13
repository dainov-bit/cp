package org.project;

import java.lang.Comparable;

/**
 * Класс работника, который хранит имя, статус и зарплату
 */
public class Worker implements Comparable<Worker> {
    private int id;
    private String name;
    private double salary;
    private Status status;

    public Worker(String name, double salary, Status status, int id) {
        this.name = name;
        this.salary = salary;
        this.id = id;
        this.status = status;
    }

    public String getStatus() {
        return this.status.get();
    }

    public Status getStatusForEdit() {
        return this.status;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public double getSalary() {
        return this.salary;
    }

    public void set(String name, Status status, double salary) {
        this.name = name;
        this.status = status;
        this.salary = salary;
    }

    @Override
    public int compareTo(Worker w) {
        if (this.salary > w.getSalary()) {
            return 1;
        } else if (this.salary < w.getSalary()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass() || o == null) {
            return false;
        }
        Worker w = (Worker) o;
        return this.id == w.getId() && this.name == w.getName() && this.salary == w.getSalary();
    }

    @Override
    public int hashCode() {
        return 31 * 31 * this.name.hashCode() * this.id;
    }


}