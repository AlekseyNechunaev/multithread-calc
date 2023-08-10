package ru.nechunaev;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("Введите n: ");
        Scanner scanner = new Scanner(System.in);
        long maxNumber = scanner.nextLong();
        final Executor executor = new Executor(maxNumber);
        executor.execute();
    }

}
