import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.io.*;
import java.nio.*;

public class Client {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(args[0], 53);
        int Qtype = 1;
        // ini tiate new tcp connection
        if (args.length < 2 || args.length > 3) {
            System.exit(0);
        }

        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        Header header = new Header((short) 10087, (short) 1, (short) 0, (short) 0,
                (short) 0);
        header.buildflags("0", "0000", "0", "0", "1", "0", "000", "0000");

        Question question = new Question(args[1], (short) 1);
        if (args.length == 3) {

            question.Qtype_toShort(args[2]);
        } else {
            question.Qtype_toShort("A");
        }

        Request request = new Request(header, question);
        byte[] bytesToSend = request.buildQuery();

        short lenght_sent = (short) bytesToSend.length;
        byte[] lenght_byte = new byte[2];
        ByteBuffer.wrap(lenght_byte).order(ByteOrder.BIG_ENDIAN).asShortBuffer().put(lenght_sent);
        out.write(lenght_byte);
        out.flush();
        out.write(bytesToSend);
        out.flush();
        System.out.println(
                " Question (NS=" + args[0] + ", NAME=" + question.GetDomain() + ", Type=" + question.type + ")");

        byte[] lenghtBuffer = new byte[2];
        in.read(lenghtBuffer);

        int lenght = ((lenghtBuffer[0] & 0xff) << 8) | (lenghtBuffer[1] & 0xff);

        byte[] responseBuffer = new byte[lenght];
        in.read(responseBuffer);

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
