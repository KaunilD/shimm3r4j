package org.example;

public class Peak {
    int start, max, end;

    public Peak(int start, int max, int end) {
        this.start = start;
        this.max = max;
        this.end = end;
    }

    public Peak() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
