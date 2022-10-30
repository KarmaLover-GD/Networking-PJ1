public class Message {
    public Header header;
    public Question question;

    public Message(Header header, Question question) {
        this.header = header;
        this.question = question;
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
