import java.io.IOException;
import java.net.Socket;
import java.io.*;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(args[0], 53);
        // initiate new tcp connection

        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        Header header = new Header((short) 17, (short) 1, (short) 0, (short) 0, (short) 0);
        header.buildflags("0", "0000", "0", "0", "1", "0", "000", "0000");

        Question question = new Question(args[1], (short) 1, (short) 1);

        Message message = new Message(header, question);
        message.buildQuery(out);

        out.flush();
        socket.close();

    }

    // public byte[] query(byte[] bytesToSend, String ServAdr) throws IOException {

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
