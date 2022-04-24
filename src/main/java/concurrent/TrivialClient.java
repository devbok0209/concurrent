package concurrent;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import lombok.AllArgsConstructor;
import utils.MessageUtils;

import java.io.IOException;
import java.net.Socket;

@AllArgsConstructor
public class TrivialClient implements Runnable{
    private static final int PORT = 8009;
    private static final int TIMEOUT = 2000;
    int clientNumber;
    private static final Logger logger = LoggerFactory.getLogger(TrivialClient.class);

    @Override
    public void run() {
        try {
            connectSendReceive(clientNumber);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void connectSendReceive(int clientNumber) throws IOException{
        logger.debug("Client %2d: connecting", clientNumber);
        Socket socket = new Socket("localhost", PORT);
        logger.debug("Client %2d: sending message", clientNumber);
        MessageUtils.sendMessage(socket, Integer.toString(clientNumber));
        logger.debug("Client %2d: getting reply", clientNumber);
        MessageUtils.getMessage(socket);
        logger.debug("Client %2d: finished", clientNumber);
        socket.isClosed();
    }
}
