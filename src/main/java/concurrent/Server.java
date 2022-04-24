package concurrent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import utils.MessageUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
@Slf4j
public class Server implements Runnable{
    ServerSocket serverSocket;
    volatile boolean keepProcessing = true;

    public Server(int port, int millisecondsTimeOut) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(millisecondsTimeOut);
    }

    @Override
    public void run() {
        log.info("Server Starting");

        while (keepProcessing) {
            try {
                log.info("accepting client");
                Socket socket = serverSocket.accept();
                log.info("got client");
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
        Runnable clientHandler = () -> {
            try {
                String message = MessageUtils.getMessage(socket);
                MessageUtils.sendMessage(socket, "Processed: " + message);
                closeIgnoringException(socket);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        };
        Thread clientConnection = new Thread(clientHandler);
        clientConnection.start();
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
