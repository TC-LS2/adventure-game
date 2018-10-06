package com.drpicox.game.utils;

import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class TimerTaskRunner {

    private Timer timer;

    public TimerTaskRunner() {
        this.timer = new Timer();
    }

    public void run(Runnable task, long millisecondsDelay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, millisecondsDelay);
    }

}
