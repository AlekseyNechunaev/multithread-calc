package ru.nechunaev.calculator;

import java.util.StringJoiner;

public class Task {
    private final long startNumber;
    private final long endNumber;
    private double result;

    public Task(long startNumber, long endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }

    public long getStartNumber() {
        return startNumber;
    }

    public long getEndNumber() {
        return endNumber;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Task.class.getSimpleName() + "[", "]")
                .add("startNumber=" + startNumber)
                .add("endNumber=" + endNumber)
                .add("result=" + result)
                .toString();
    }
}
