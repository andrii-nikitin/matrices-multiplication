package ua.anikitin.matrices;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedExecutor {
    public static final int THREAD_POOL_CAPACITY = 10;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_CAPACITY);

    public <T> Future<T> submitTask(Callable<T> callableTask) {
        return executor.submit(callableTask);
    }
}
