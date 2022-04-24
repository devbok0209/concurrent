package concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorClientScheduler implements ClientScheduler {
    Executor executor;

    public ExecutorClientScheduler(int availableThreads) {
        executor = Executors.newFixedThreadPool(availableThreads);
    }

    @Override
    public void schedule(ClientRequestProcessor requestProcessor) {
        Runnable runnable = requestProcessor::process;

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
