package javaOne.javaConcurrency.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alexi on 09.01.2026
 */

class ComplexTaskExecutor {
    private final int numberOfTasks;
    private final AtomicInteger totalResult = new AtomicInteger(0);
    private CyclicBarrier barrier;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public void executeTasks(int taskCount) {
        totalResult.set(0);

        barrier = new CyclicBarrier(taskCount, () -> {
            System.out.println("\n" + Thread.currentThread().getName() +
                    ": Все задачи завершены! Общий результат: " + totalResult.get() + "\n");
        });

        ExecutorService executorService = Executors.newFixedThreadPool(taskCount);
        List<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < taskCount; i++) {
            final int taskId = i;
            tasks.add(() -> {
                ComplexTask task = new ComplexTask(taskId, totalResult);
                task.execute();

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                    System.err.println(Thread.currentThread().getName() + " был прерван: " + e.getMessage());
                }
            });
        }

        for (Runnable task : tasks) {
            executorService.execute(task);
        }

        executorService.shutdown();
    }

    public int getTotalResult() {
        return totalResult.get();
    }
}