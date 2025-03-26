package model;

public class Meaning {
    private String content;

    public Meaning(){}

    public Meaning(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}