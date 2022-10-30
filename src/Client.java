import java.io.IOException;
import java.net.Socket;
import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException {

    }

    // public byte[] query(byte[] bytesToSend, String ServAdr) throws IOException {
    // // initiate new tcp connection
    // Socket socket = new Socket(ServAdr, 53);
    // OutputStream out = socket.getOutputStream();
    // InputStream in = socket.getInputStream();

    // // Sends Query
    // out.write(bytesToSend);
    // out.flush();

    // // retrieve response lenght
    // byte[] lenghtBuffer = new byte[2];
    // in.read(lenghtBuffer);

    // int lenght = ((lenghtBuffer[0] & 0xff) << 8) | (lenghtBuffer[1] & 0xff);

    // byte[] responseBuffer = new byte[lenght];

    // in.read(responseBuffer);
    // socket.close();
    // return responseBuffer;

    // }

}
