import java.io.DataOutputStream;
import java.nio.*;

public class Question {
    private byte[][] QNAME;
    private short Qname_sz;
    private short Qtype;
    private short Qclass;
    public String domain;
    public String type;

    public Question(String domain, short Qclass) {
        String[] tmp = domain.split("\\.");

        // FIND how many parts in domain
        int domain_parts = 1;
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
        this.Qclass = Qclass;
    }

    public void Qtype_toShort(String type) {
        if (type.equals("TXT")) {
            this.Qtype = 16;
            this.type = "TXT";
        } else {
            this.Qtype = 1;
            this.type = "A";
        }

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
        byte[] tmp = new byte[2];
        ByteBuffer.wrap(tmp).order(ByteOrder.BIG_ENDIAN).asShortBuffer().put(this.Qtype);
        return tmp;
    }

    public void setQtype(short Qtype) {
        this.Qtype = Qtype;
    }

    public byte[] getQclass() {
        byte[] tmp = new byte[2];
        ByteBuffer.wrap(tmp).order(ByteOrder.BIG_ENDIAN).asShortBuffer().put(this.Qclass);
        return tmp;
    }

    public void setQclass(short Qclass) {
        this.Qclass = Qclass;
    }

}
