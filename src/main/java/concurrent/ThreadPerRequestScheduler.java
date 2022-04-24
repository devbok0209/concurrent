package concurrent;

public class ThreadPerRequestScheduler implements ClientScheduler{
    @Override
    public void schedule(ClientRequestProcessor requestProcessor) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                requestProcessor.process();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}
