import java.io.IOError;
import java.io.IOException;
import java.io.*;
import java.nio.*;

public class Request {
    public Header header;
    public Question question;

    public Request(Header header, Question question) {
        this.header = header;
        this.question = question;
    }

    public byte[] buildQuery() throws IOException {

        // write header
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(header.getID());
        out.write(header.getFlags());
        out.write(header.getQDCOUNT());
        out.write(header.getANCOUNT());
        out.write(header.getNSCOUNT());
        out.write(header.getARCOUNT());

        // write question
        for (short i = 0; i < question.getQName_sz(); i++) {
            out.write((byte) question.getQNAME()[i].length);
            out.write(question.getQNAME()[i]);

        }
        out.write((byte) 0);
        out.write(question.getQtype());
        out.write(question.getQclass());

        byte[] output = out.toByteArray();
        return output;
    }

    public Header getHeader() {
        return this.header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
