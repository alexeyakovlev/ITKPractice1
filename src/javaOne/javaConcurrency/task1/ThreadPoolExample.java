package javaOne.javaConcurrency.task1;

/**
 * Created by alexi on 09.01.2026
 */

public class ThreadPoolExample {
    public static void main(String[] args) {
        BlockingQueue<Runnable> taskQueue = new BlockingQueue<>(10);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                final int taskId = i;
                try {
                    taskQueue.enqueue(() -> {
                        System.out.println("Выполняется задача " + taskId +
                                " в потоке " + Thread.currentThread().getName());
                    });
                    System.out.println("Задача " + taskId + " добавлена в очередь");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = taskQueue.dequeue();
                    task.run();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}