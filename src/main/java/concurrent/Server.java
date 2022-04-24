package concurrent;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import lombok.SneakyThrows;
import utils.MessageUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    ServerSocket serverSocket;
    volatile boolean keepProcessing = true;

    public Server(int port, int millisecondsTimeOut) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(millisecondsTimeOut);
    }

    @Override
    public void run() {
        logger.debug("Server Starting");

        while (keepProcessing) {
            try {
                logger.debug("accepting client");
                Socket socket = serverSocket.accept();
                logger.debug("got client");
                process(socket);
            } catch (Exception exception) {
                handle(exception);
            }
        }
    }

    public void stopProcessing() {
        keepProcessing = false;
        closeIgnoringException(serverSocket);
    }

    private void handle(Exception exception) {
        if (!(exception instanceof SocketException)) {
            exception.printStackTrace();
        }
    }

    private void process(Socket socket) {
        if (socket == null) return;

        try {
            logger.debug("Server: getting message");
            String message = MessageUtils.getMessage(socket);
            Thread.sleep(1000);
            logger.debug("Server: sending reply: {}", message);
            MessageUtils.sendMessage(socket, "Processed: " + message);
            logger.debug("Server: send");
            closeIgnoringException(socket);
        } catch (Exception exception) {
            exception.printStackTrace();

            if (exception instanceof InterruptedException) {
                logger.error("Interrupted!!");
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        }
    }
    @SneakyThrows(IOException.class)
    private void closeIgnoringException(Socket socket) {
        if (socket != null) {
            socket.close();
        }
    }

    @SneakyThrows(IOException.class)
    private void closeIgnoringException(ServerSocket serverSocket) {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
}
