package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static final ReentrantReadWriteLock reentrant = new ReentrantReadWriteLock();
    private static final Lock WRITE_LOCK = reentrant.writeLock();
    private static final Lock READ_LOCK = reentrant.readLock();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };
    private static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
            }
        };
        thread0.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService completionService = new ExecutorCompletionService(service);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = service.submit(() -> {
//            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(threadLocal.get().format(new Date()));
                }
                return 5;
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        service.shutdown();
        System.out.println(counter);
        System.out.println(mainConcurrency.atomicCounter.get());
    }

    private void inc() {
//        lock.lock();
//        try{
        atomicCounter.incrementAndGet();
//            counter++;
    }
//            finally {
//            lock.unlock();
//        }
}