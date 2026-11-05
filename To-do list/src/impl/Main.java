package impl;

import impl.service.TaskManager;

public class Main {
    static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.start();

    }
}
