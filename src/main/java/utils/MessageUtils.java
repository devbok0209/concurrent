package utils;

import java.io.*;
import java.net.Socket;

public class MessageUtils {
    public static void sendMessage(Socket socket, String message) throws IOException {
        OutputStream stream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream);
        objectOutputStream.writeUTF(message);
        objectOutputStream.flush();
    }

    public static String getMessage(Socket socket) throws IOException {
        InputStream stream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(stream);
        return objectInputStream.readUTF();
    }

}
