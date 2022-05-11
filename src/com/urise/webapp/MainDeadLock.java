package com.urise.webapp;

public class MainDeadLock {

    public static void main(String[] args) {
        final Object obj_1 = new Object();
        final Object obj_2 = new Object();
        new Thread(() -> {
            System.out.println("Thread is executing " + Thread.currentThread().getName());
            deadlock(obj_1, obj_2);
        }).start();
        new Thread(() -> {
            System.out.println("Thread is executing " + Thread.currentThread().getName());
            deadlock(obj_2, obj_1);
        }).start();
    }

    public static void deadlock(Object obj_1, Object obj_2) {
        System.out.println("Thread " + Thread.currentThread().getName() + " trying to grab an object 1");
        synchronized (obj_1) {
            System.out.println("Thread " + Thread.currentThread().getName() + " to grab an object 1");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " trying to grab an object 2");
            synchronized (obj_2) {
                System.out.println("Thread " + Thread.currentThread().getName() + " to grab an object 2");
            }
        }
    }
}