package com.company;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.UUID;

public record Producer( Deque<String> list,RateLimiter rateLimiter,int maxQueue) implements Runnable{

    public void produce() throws InterruptedException {
        while (true) {
            {
                if(rateLimiter.acquire()) {
                    String now = LocalDateTime.now().toString();
                    UUID uuid = UUID.nameUUIDFromBytes(now.getBytes(StandardCharsets.UTF_8));
                    synchronized (list) {
                        list.add(LocalDateTime.now() + " " + "UUID: " + uuid);
                        list.notify();
                        if (list.size() >= maxQueue) {
                            Thread.sleep(10000);
                        }// Producer останавливается при превышении максимального кол-ва элементов в очереди
                    }

                    System.out.println("Producer отправил сообщение.");
                } else {
                    System.out.println("Producer пропустит сообщение из-за ограничения.");;
                }

                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Producer остановлен");
                    break;
                }
            }
        }
    }
    @Override
    public void run() {
        try {
            produce();
        } catch (InterruptedException e) {
            System.out.println("Producer остановлен");
        }

    }
}
