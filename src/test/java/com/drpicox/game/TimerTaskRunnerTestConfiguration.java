package com.drpicox.game;

import com.drpicox.game.utils.TimerTaskRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TimerTaskRunnerTestConfiguration {

    @Bean
    @Primary
    public TimerTaskRunner timerTaskRunner() {
        return new TimerTaskRunner() {
            @Override
            public void run(Runnable task) {
                System.out.println("TimerTaskRunner =+=+=+=+=+=+=+=+=+=+=+=+=========== OVERRIDE");
            }
        };
    }
}
