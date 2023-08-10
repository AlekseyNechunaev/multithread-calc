package ru.nechunaev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nechunaev.calculator.Task;
import ru.nechunaev.calculator.TaskCalculator;
import ru.nechunaev.exception.ParallelCalculationException;
import ru.nechunaev.util.Const;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Executor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Executor.class);
    private final Collection<TaskCalculator> tasks;

    public Executor(final long maxNumber) {
        this.tasks = createTasks(maxNumber);
    }

    public double execute() {
        LOGGER.info("Threads count: {}", Const.MAX_THREADS);
        long startTime = System.nanoTime();
        LOGGER.info("calculate start time (ns): {}", startTime);
        ExecutorService executorService = Executors.newFixedThreadPool(Const.MAX_THREADS);
        final String errorMessage = "An error occurred while performing calculations, it is impossible to continue further";
        List<Future<Task>> futureResults;
        try {
            futureResults = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            LOGGER.error(errorMessage, e);
            throw new ParallelCalculationException(errorMessage, e);
        }
        executorService.shutdown();
        double result = futureResults.stream()
                .mapToDouble(future -> {
                    try {
                        return future.get().getResult();
                    } catch (InterruptedException | ExecutionException e) {
                        LOGGER.error(errorMessage, e);
                        throw new ParallelCalculationException(errorMessage, e);
                    }
                }).sum();
        long endTime = System.nanoTime();
        LOGGER.info("calculate end time (ns): {}", endTime);
        LOGGER.info("calculation execution time (ns): {}", endTime - startTime);
        LOGGER.info("result = {}", result);
        return result;
    }

    private Collection<TaskCalculator> createTasks(final long maxNumber) {
        final List<TaskCalculator> tasks = new ArrayList<>(Const.MAX_THREADS);
        long step = maxNumber / Const.MAX_THREADS;
        final boolean isDivNumberIntoThreadCount = maxNumber % Const.MAX_THREADS == 0;
        final long divRemains = maxNumber % Const.MAX_THREADS;
        long position = 0;
        while (position < maxNumber) {
            if (!isDivNumberIntoThreadCount && position == maxNumber - divRemains) {
                step += divRemains;
            }
            LOGGER.info("step = {}", step);
            long startNumber = position + 1;
            long endNumber = startNumber + step;
            Task task = new Task(startNumber, endNumber);
            TaskCalculator calculator = new TaskCalculator(task);
            tasks.add(calculator);
            position += step;
        }
        LOGGER.info("tasks = {}", tasks);
        return tasks;
    }
}
