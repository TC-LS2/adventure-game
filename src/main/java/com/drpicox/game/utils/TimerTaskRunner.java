package com.drpicox.game.utils;

import org.springframework.stereotype.Service;

@Service
public class TimerTaskRunner {

    public void run(Runnable task) {
        System.out.println("TimerTaskRunner ++++++++++++ default");
    }

}
