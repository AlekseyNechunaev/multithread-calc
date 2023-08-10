package ru.nechunaev.exception;

public class ParallelCalculationException extends RuntimeException {

    public ParallelCalculationException() {
    }

    public ParallelCalculationException(String message) {
        super(message);
    }

    public ParallelCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
