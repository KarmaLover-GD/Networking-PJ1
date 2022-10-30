import java.io.DataOutputStream;

public class Question {
    private byte[] QNAME;
    private short Qtype;
    private short Qclass;

    public Question(String domain, short Qtype, short Qclass) {
        String[] tmp = domain.split("\\.");
        // FIND how many parts in domain
        int domain_parts = 0;
        for (int i = 0; i < domain.length(); i++) {
            if (domain.charAt(i) == '.')
                domain_parts += 1;

        }
        // filling QNAME
        for (int i = 0; i < domain_parts; i++) {
            QNAME = tmp[i].getBytes();

        }
        this.Qtype = Qtype;
        this.Qclass = Qclass;
    }

    public byte[] getQNAME() {
        return this.QNAME;
    }

    public void setQNAME(byte[] QNAME) {
        this.QNAME = QNAME;
    }

    public short getQtype() {
        return this.Qtype;
    }

    public void setQtype(short Qtype) {
        this.Qtype = Qtype;
    }

    public short getQclass() {
        return this.Qclass;
    }

    public void setQclass(short Qclass) {
        this.Qclass = Qclass;
    }

}
