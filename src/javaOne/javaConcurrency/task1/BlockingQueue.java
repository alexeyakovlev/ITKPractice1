package javaOne.javaConcurrency.task1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by alexi on 09.01.2026
 */

public class BlockingQueue<T> {
    private final Queue<T> queue;
    private final int capacity;

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be greater than 0");
        }

        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(item);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        T item = queue.poll();
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }

    public int capacity() {
        return capacity;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized boolean isFull() {
        return queue.size() == capacity;
    }
}
