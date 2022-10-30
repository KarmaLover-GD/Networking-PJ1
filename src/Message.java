import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;

public class Message {
    public Header header;
    public Question question;

    public Message(Header header, Question question) {
        this.header = header;
        this.question = question;
    }

    public void buildQuery(OutputStream out) throws IOException {

        // write header
        out.write(header.getID());
        out.write(header.getFlags());
        out.write(header.getQDCOUNT());
        out.write(header.getANCOUNT());
        out.write(header.getNSCOUNT());
        out.write(header.getARCOUNT());

        // write question
        for (short i = 0; i < question.getQName_sz(); i++) {
            out.write(question.getQNAME()[i].length);
            out.write(question.getQNAME()[i]);
        }
        out.write(question.getQtype());
        out.write(question.getQclass());
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
