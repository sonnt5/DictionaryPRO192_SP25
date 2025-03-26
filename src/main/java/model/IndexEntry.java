package model;

public class IndexEntry {
    private String word;
    private long begin;
    private int len;

    public IndexEntry(){}

    public IndexEntry(String word, long begin, int len) {
        this.word = word;
        this.begin = begin;
        this.len = len;
    }

    public String getWord() {
        return word;
    }

    public long getBegin() {
        return begin;
    }

    public int getLen() {
        return len;
    }

    @Override
    public String toString() {
        return word + " " + begin + " " + len;
    }
}