package org.practice.java.agentExample;

import org.practice.java.agent.Measured;

import java.util.Random;

public class RunExample {
    private Random random = new Random();

    @Measured
    public void doSleep() {
        try {
            System.out.println("doSleep");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Measured
    public void doTask() {
        try {
            System.out.println("doTask");
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Measured
    public void doWork() {
        for (int i=0; i < random.nextInt(10);++i) {
            doTask();
        }
    }

    public static void main(String[] args) {
        RunExample runExample = new RunExample();
        while (true) {
            runExample.doWork();
            runExample.doSleep();
        }
    }
}
