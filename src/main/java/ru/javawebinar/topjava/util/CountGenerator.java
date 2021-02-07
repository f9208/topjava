package ru.javawebinar.topjava.util;

import java.util.concurrent.locks.ReentrantLock;

public class CountGenerator {
    private static long counter = 0;
    private static ReentrantLock counterLock = new ReentrantLock(true);

    public static long getCurrentValue() {
        return counter;
    }

    public static Long incrementCounter() {
        counterLock.lock();
        try {
            return ++counter;
        } finally {
            counterLock.unlock();
        }
    }
}
