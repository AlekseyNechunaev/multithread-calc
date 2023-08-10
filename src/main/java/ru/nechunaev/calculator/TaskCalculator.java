package ru.nechunaev.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringJoiner;
import java.util.concurrent.Callable;

public class TaskCalculator implements Callable<Task> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskCalculator.class);
    private final Task task;

    public TaskCalculator(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public Task call() {
        double result = 0;
        for (long number = task.getStartNumber(); number < task.getEndNumber(); number++) {
            result += 1D / number;
        }
        task.setResult(result);
        final String threadName = Thread.currentThread().getName();
        LOGGER.info("Thread {} completed the calculation with result {}", threadName, result);
        return task;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TaskCalculator.class.getSimpleName() + "[", "]")
                .add("task=" + task)
                .toString();
    }
}
