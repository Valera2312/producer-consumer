package com.company;

import java.util.Deque;

public record Consumer(Deque<String> list) implements Runnable {

    public void consume() throws InterruptedException {

        while (true) {
            synchronized (list) {
                while (list.isEmpty()) {
                    list.wait(); // Consumer ожидает, пока список пустой
                }
                String value = list.removeFirst();
                System.out.println("Consumer получил сообщение: " + value);
            }

            Thread.sleep(1000);

            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Consumer остановлен");
                break;
            }
        }
    }

    @Override
    public void run() {
        try {
            consume();
        } catch (InterruptedException e) {
            System.out.println("Consumer остановлен");
        }
    }
}
