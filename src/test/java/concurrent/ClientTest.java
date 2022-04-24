package concurrent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private static final int PORT = 8009;
    private static final int TIMEOUT = 2000;

    Server server;
    Thread serverThread;

    @BeforeEach
    void createServer() throws Exception{
        try {
            server = new Server(PORT, TIMEOUT);
            serverThread = new Thread(server);
            serverThread.start();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @AfterEach
    void shutdownServer() throws InterruptedException {
        if (server != null) {
            server.stopProcessing();
            serverThread.join();
        }
    }

    @Test
    @Timeout(10000)
    void shouldRunInUnder10Secound() throws Exception {
        Thread[] threads = createThreads();
        startAllTHreads(threads);
        waitForAllTHreadsTOFinish(threads);

    }
    private Thread[] createThreads() {
        return new Thread[10];
    }

    private void waitForAllTHreadsTOFinish(Thread[] threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private void startAllTHreads(Thread[] threads) {
        for (int clientNumber = 0; clientNumber < threads.length; clientNumber++) {
            threads[clientNumber] = new Thread(new TrivialClient(clientNumber));
            threads[clientNumber].start();
        }
    }
}