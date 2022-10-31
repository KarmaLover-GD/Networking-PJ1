import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;

public class test {
    public static void main(String[] args) throws IOException {
        InetAddress ipAddress = InetAddress.getByName("1.1.1.1");// cloudflare

        Random random = new Random();
        short ID = (short) random.nextInt(32767);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        short requestFlags = Short.parseShort("0000000100000000", 2);
        ByteBuffer flagsByteBuffer = ByteBuffer.allocate(2).putShort(requestFlags);
        byte[] flagsByteArray = flagsByteBuffer.array();

        short QDCOUNT = 1;
        short ANCOUNT = 0;
        short NSCOUNT = 0;
        short ARCOUNT = 0;

        dataOutputStream.writeShort(ID);
        dataOutputStream.write(flagsByteArray);
        dataOutputStream.writeShort(QDCOUNT);
        dataOutputStream.writeShort(ANCOUNT);
        dataOutputStream.writeShort(NSCOUNT);
        dataOutputStream.writeShort(ARCOUNT);
        String domain = "medium.com";
        String[] domainParts = domain.split("\\.");

        for (int i = 0; i < domainParts.length; i++) {
            byte[] domainBytes = domainParts[i].getBytes();
            dataOutputStream.writeByte(domainBytes.length);
            dataOutputStream.write(domainBytes);
            System.out.println(domainBytes + " " + domainBytes.length);
        }
        // No more parts
        dataOutputStream.writeByte(0);
        // Type 1 = A (Host Request)
        dataOutputStream.writeShort(1);
        // Class 1 = IN
        dataOutputStream.writeShort(1);

        byte[] dnsFrame = byteArrayOutputStream.toByteArray();

        System.out.println("Sending: " + dnsFrame.length + " bytes");
        for (int i = 0; i < dnsFrame.length; i++) {
            System.out.print(String.format("%s", dnsFrame[i]) + " ");
        }
        System.out.println();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket dnsReqPacket = new DatagramPacket(dnsFrame, dnsFrame.length, ipAddress, 53);
        socket.send(dnsReqPacket);
    }
}
