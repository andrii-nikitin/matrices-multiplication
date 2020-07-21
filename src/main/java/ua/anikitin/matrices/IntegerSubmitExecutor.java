package ua.anikitin.matrices;

import java.util.concurrent.*;

public class IntegerSubmitExecutor {
    public static final int THREAD_POOL_CAPACITY = 10;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_CAPACITY);
    CompletionService<Integer[]> completionService =
            new ExecutorCompletionService<>(executor);

    public Future<Integer[]> submitTask(Callable<Integer[]> callableTask) {
        return completionService.submit(callableTask);
    }

    public Future<Integer[]> take() throws InterruptedException {
        return completionService.take();
    }
}
