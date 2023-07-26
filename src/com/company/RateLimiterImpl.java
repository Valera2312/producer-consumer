package com.company;

public final class RateLimiterImpl implements RateLimiter {

    private final long delayBetweenRequests; // Время между разрешениями в миллисекундах
    private final Object lock = new Object();

    public RateLimiterImpl(int maxRequestsPerSecond) {
        this.delayBetweenRequests = 1000 / maxRequestsPerSecond;
    }

    @Override
    public boolean acquire() {
        synchronized (lock) {
            try {
                Thread.sleep(delayBetweenRequests);
                return true;
            } catch (InterruptedException e) {
                return false;
            }
        }
    }
}
