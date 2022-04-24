package concurrent;

public interface ClientScheduler {
    void schedule(ClientRequestProcessor requestProcessor);
}
