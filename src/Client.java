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
                "Question (NS=" + server + ", NAME=" + question.GetDomain() + ", Type=" + question.type + ")");

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

    public void decode_response(byte[] responseBuffer) throws IOException {

        ByteArrayInputStream ResponseStream = new ByteArrayInputStream(responseBuffer);
        DataInputStream bb = new DataInputStream(ResponseStream);

        short ID = bb.readShort();
        short flags = bb.readShort();
        short QD = bb.readShort();
        short AN = bb.readShort();
        short NS = bb.readShort();
        short AR = bb.readShort();
        Header header = new Header(ID, flags, QD, AN, NS, AR);

        String domain = "";

        int dom_sz = bb.read();
        byte[] domain_Bt = new byte[256];
        for (int i = 0, j = 0; i < dom_sz + 1; i++) {
            if (i == dom_sz) {
                i = 0;
                j += dom_sz;

                dom_sz = bb.readByte();
                if (dom_sz == (byte) 0)
                    break;

                domain_Bt[i + j] = (byte) 46;
                j++;

            }
            domain_Bt[i + j] = bb.readByte();

            domain = new String(domain_Bt);

        }

        short Qtype = bb.readShort();
        short Qclass = bb.readShort();
        Question question = new Question(domain, Qclass, Qtype);

        for (int i = 0; i < AN; i++) {

            byte tmp;
            for (int j = 0; j < 2; j++) {
                tmp = bb.readByte();
            }
            short type = bb.readShort();
            System.out.print("Answer (Type=" + question.Qtype_toString(type) + ", ");
            short CLASS = bb.readShort();
            ByteBuffer buffer = ByteBuffer.allocate(4);
            int TTL = bb.readInt();
            System.out.print("TTL=" + TTL + ", ");

            short RDlenght = bb.readShort();
            byte[] data = new byte[RDlenght];
            for (int k = 0; k < RDlenght; k++) {
                data[k] = bb.readByte();
            }
            String strdata = new String(data);
            System.out.println("DATA =" + strdata + ")");

        }

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
        client.decode_response(response);
    }
}
