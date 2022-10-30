import java.io.DataOutputStream;
import java.nio.*;

public class Question {
    private byte[][] QNAME;
    private short Qname_sz;
    private short Qtype;
    private short Qclass;
    public String domain;

    public Question(String domain, short Qtype, short Qclass) {
        String[] tmp = domain.split("\\.");
        // FIND how many parts in domain
        int domain_parts = 0;
        this.domain = domain;
        for (int i = 0; i < domain.length(); i++) {
            if (domain.charAt(i) == '.')
                domain_parts += 1;
        }
        // filling QNAME
        QNAME = new byte[domain_parts][];
        for (int i = 0; i < domain_parts; i++) {
            QNAME[i] = tmp[i].getBytes();

        }
        this.Qname_sz = (short) domain_parts;
        this.Qtype = Qtype;
        this.Qclass = Qclass;
    }

    public String GetDomain() {
        return this.domain;
    }

    public byte[][] getQNAME() {
        return this.QNAME;
    }

    public short getQName_sz() {
        return this.Qname_sz;
    }

    public void setQNAME(byte[][] QNAME) {
        this.QNAME = QNAME;
    }

    public byte[] getQtype() {
        byte[] tmp = new byte[10];
        ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(this.Qtype);
        return tmp;
    }

    public void setQtype(short Qtype) {
        this.Qtype = Qtype;
    }

    public byte[] getQclass() {
        byte[] tmp = new byte[10];
        ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(this.Qclass);
        return tmp;
    }

    public void setQclass(short Qclass) {
        this.Qclass = Qclass;
    }

}
