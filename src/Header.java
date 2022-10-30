import javax.swing.event.AncestorEvent;

import java.io.ObjectInputStream.GetField;
import java.nio.ByteBuffer;

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
        short flags_short = Short.parseShort(flags);
        ByteBuffer flagsBB = ByteBuffer.allocate(2).putShort(flags_short);
        this.flags = flagsBB.array();

    }

    public short getID() {
        return this.ID;
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

    public short getQDCOUNT() {
        return this.QDCOUNT;
    }

    public void setQDCOUNT(short QDCOUNT) {
        this.QDCOUNT = QDCOUNT;
    }

    public short getANCOUNT() {
        return this.ANCOUNT;
    }

    public void setANCOUNT(short ANCOUNT) {
        this.ANCOUNT = ANCOUNT;
    }

    public short getNSCOUNT() {
        return this.NSCOUNT;
    }

    public void setNSCOUNT(short NSCOUNT) {
        this.NSCOUNT = NSCOUNT;
    }

    public short getARCOUNT() {
        return this.ARCOUNT;
    }

    public void setARCOUNT(short ARCOUNT) {
        this.ARCOUNT = ARCOUNT;
    }

}
