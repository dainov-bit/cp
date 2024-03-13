package org.project;

import java.util.Comparator;

public class WorkerComparator implements Comparator<Worker> {
    @Override
    public int compare(Worker w1, Worker w2) {
        if (w1.getId() > w2.getId()) {
            return 1;
        } else if (w1.getId() < w2.getId()) {
            return -1;
        } else {
            return 0;
        }
    }

}