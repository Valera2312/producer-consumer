package com.company;


import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;


public class Main {
    public static void main(String[] args) throws InterruptedException {


        Deque<String> list = new ConcurrentLinkedDeque<>();

         //объект огранечителя 
        RateLimiter limiter = new RateLimiterImpl(100);
        // Максимальный размер очереди
        int maxQueue = 1000;

        Consumer consumer = new Consumer(list);
        Producer producer = new Producer(list,limiter,maxQueue);

        // Создаем поток Продюсер
        Thread producerThread = new Thread(producer);

        // Создаем поток Консьюмер
        Thread consumerThread = new Thread(consumer);


        producerThread.start();
        consumerThread.start();
        Thread.sleep(60000);
        producerThread.interrupt();
        consumerThread.interrupt();

    }


}
