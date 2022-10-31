import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.io.*;
import java.nio.*;

import javax.management.Query;

public class Client {
    public Client() {

    }

    public byte[] query(String server, String domain, String type) throws IOException {

        Socket socket = new Socket(server, 53);

        // ini tiate new tcp connection

        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        // Initialise header
        Header header = new Header((short) 10087, (short) 1, (short) 0, (short) 0,
                (short) 0);

        // Fill in header flags
        header.buildflags("0", "0000", "0", "0", "1", "0", "000", "0000");

        // initialise Questions
        Question question = new Question(domain, (short) 1);
        question.Qtype_toShort(type);
        // fill in Question type

        // retrieve request as an array of bytes
        Request request = new Request(header, question);
        byte[] bytesToSend = request.buildQuery();

        // Send lenght of the request
        short lenght_sent = (short) bytesToSend.length;
        byte[] lenght_byte = new byte[2];
        ByteBuffer.wrap(lenght_byte).order(ByteOrder.BIG_ENDIAN).asShortBuffer().put(lenght_sent);
        out.write(lenght_byte);
        out.flush();

        // Send request
        out.write(bytesToSend);
        out.flush();

        // display the question
        System.out.println(
                " Question (NS=" + server + ", NAME=" + question.GetDomain() + ", Type=" + question.type + ")");

        // retrieve answel lenght
        byte[] lenghtBuffer = new byte[2];
        in.read(lenghtBuffer);

        int lenght = ((lenghtBuffer[0] & 0xff) << 8) | (lenghtBuffer[1] & 0xff);

        // retrieve full answer
        byte[] responseBuffer = new byte[lenght];
        in.read(responseBuffer);
        socket.close();
        return responseBuffer;
    }

    public Request decode_response(byte[] responseBuffer) {
        ByteBuffer bb = ByteBuffer.allocate(responseBuffer.length);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(responseBuffer);

        short ID = bb.getShort(0);
        short flags = bb.getShort(1);
        short QD = bb.getShort(2);
        short AN = bb.getShort(3);
        short NS = bb.getShort(4);
        short AR = bb.getShort(5);
        Header header = new Header(ID, flags, QD, AN, NS, AR);
        // header.buildflags(null, null, null, null, null, null, null, null);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        if (args.length < 2 || args.length > 3) {
            System.exit(0);
        }
        byte[] response;
        if (args.length == 3) {
            response = client.query(args[0], args[1], args[2]);
        } else {
            response = client.query(args[0], args[1], "A");
        }
    }
}
