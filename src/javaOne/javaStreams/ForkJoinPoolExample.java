package javaOne.javaStreams;

/**
 * Created by alexi on 27.12.2025
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        int n = 10;

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialTask factorialTask = new FactorialTask(1, n);

        long result = forkJoinPool.invoke(factorialTask);

        System.out.println("Факториал " + n + "! = " + result);
    }
}

class FactorialTask extends RecursiveTask<Long> {
    private final int start;
    private final int end;
    private static final int THRESHOLD = 5;
    public FactorialTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - start) <= THRESHOLD) {
            return computeSequentially();
        }

        int middle = start + (end - start) / 2;

        FactorialTask leftTask = new FactorialTask(start, middle);
        FactorialTask rightTask = new FactorialTask(middle + 1, end);

        leftTask.fork();

        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();

        return leftResult * rightResult;
    }

    private long computeSequentially() {
        long result = 1;
        for (int i = start; i <= end; i++) {
            result *= i;
        }
        return result;
    }
}