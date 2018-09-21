package com.drpicox.game.mocks;

import com.drpicox.game.utils.TimerTaskRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimerTaskRunnerMock extends TimerTaskRunner {

    private List<Runnable> tasks = new ArrayList<>();

    @Override
    public void run(Runnable task, long delay) {
        tasks.add(task);
    }

    public void fireTaks() {
        System.out.println("... time skip ...");
        for (Runnable task : tasks) {
            task.run();
        }
        tasks.clear();
    }

    public void clear() {
        tasks.clear();
    }
}
