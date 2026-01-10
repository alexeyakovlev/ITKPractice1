package javaOne.javaConcurrency.task2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alexi on 09.01.2026
 */

class ComplexTask {
    private final int taskId;
    private final AtomicInteger totalResult;

    public ComplexTask(int taskId, AtomicInteger totalResult) {
        this.taskId = taskId;
        this.totalResult = totalResult;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " выполняет задачу " + taskId);

        int localResult = 0;
        for (int i = 0; i < 1000; i++) {
            localResult += (int) (Math.random() * 10);
        }

        totalResult.addAndGet(localResult);
        System.out.println(Thread.currentThread().getName() + " завершил задачу " + taskId +
                " с локальным результатом: " + localResult);
    }
}