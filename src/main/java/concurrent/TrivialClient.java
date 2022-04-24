package concurrent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import utils.MessageUtils;

import java.io.IOException;
import java.net.Socket;

@AllArgsConstructor
@Slf4j
public class TrivialClient implements Runnable{
    private static final int PORT = 8009;
    private static final int TIMEOUT = 2000;
    int clientNumber;

    @Override
    public void run() {
        try {
            connectSendReceive(clientNumber);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void connectSendReceive(int clientNumber) throws IOException{
        log.info("Client {}: connecting", clientNumber);
        Socket socket = new Socket("localhost", PORT);
        log.info("Client {}: sending message", clientNumber);
        MessageUtils.sendMessage(socket, Integer.toString(clientNumber));
        log.info("Client {}: getting reply", clientNumber);
        MessageUtils.getMessage(socket);
        log.info("Client {}: finished", clientNumber);
        socket.isClosed();
    }
}
