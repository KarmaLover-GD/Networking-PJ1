import javax.swing.event.AncestorEvent;

import java.io.ObjectInputStream.GetField;
import java.nio.*;

public class Header {
    private short ID; // Unique identifier
    private byte[] flags; // Array of bits
    private short QDCOUNT;
    private short ANCOUNT;
    private short NSCOUNT;
    private short ARCOUNT;

    public Header(short ID, short QDCOUNT, short ANCOUNT, short NSCOUNT, short ARCOUNT) {
        this.ID = ID;
        this.QDCOUNT = QDCOUNT;
        this.ANCOUNT = ANCOUNT;
        this.NSCOUNT = NSCOUNT;
        this.ARCOUNT = ARCOUNT;
    }

    public void buildflags(String QR, String Opcode, String AA, String TC, String RD,
            String RA, String Z, String RCODE) {
        String flags = QR;
        flags += (Opcode + AA + TC + RD + RA + Z + RCODE);
        short flags_short = Short.parseShort(flags, 2);
        ByteBuffer flagsBB = ByteBuffer.allocate(2).putShort(flags_short);
        this.flags = flagsBB.array();

    }

    public byte[] getID() {
        byte[] tmp = new byte[2];
        ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(this.ID);
        return tmp;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public byte[] getFlags() {
        return this.flags;
    }

    public void setFlags(byte[] flags) {
        this.flags = flags;
    }

    public byte[] getQDCOUNT() {
        byte[] tmp = new byte[2];
        ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(this.QDCOUNT);
        return tmp;
    }

    public void setQDCOUNT(short QDCOUNT) {
        this.QDCOUNT = QDCOUNT;
    }

    public byte[] getANCOUNT() {
        byte[] tmp = new byte[2];
        ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(this.ANCOUNT);
        return tmp;
    }

    public void setANCOUNT(short ANCOUNT) {
        this.ANCOUNT = ANCOUNT;
    }

    public byte[] getNSCOUNT() {
        byte[] tmp = new byte[2];
        ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(this.NSCOUNT);
        return tmp;
    }

    public void setNSCOUNT(short NSCOUNT) {
        this.NSCOUNT = NSCOUNT;
    }

    public byte[] getARCOUNT() {
        byte[] tmp = new byte[2];
        ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(this.ARCOUNT);
        return tmp;
    }

    public void setARCOUNT(short ARCOUNT) {
        this.ARCOUNT = ARCOUNT;
    }

}
