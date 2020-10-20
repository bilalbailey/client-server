package com.company;

import java.util.Timer;
import java.util.concurrent.BlockingQueue;

/*
 * ManageQueue class is used to manage the threads in ConnectionClient and
 * set the time to 10 seconds for ManageTimer
 * */
public class ManageQueue implements Runnable {
    private BlockingQueue<Integer> blockingQueue;
    private Timer timer;


    public ManageQueue(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
        timer = new Timer();
        timer.schedule(
                new ManageTimer()
                , 0
                , 10000);
    }

    @Override
    public void run() {

    }
}
