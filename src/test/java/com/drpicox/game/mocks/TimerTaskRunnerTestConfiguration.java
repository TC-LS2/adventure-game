package com.drpicox.game.mocks;

import com.drpicox.game.utils.TimerTaskRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TimerTaskRunnerTestConfiguration {

    private TimerTaskRunnerMock timerTaskRunnerMock;

    public TimerTaskRunnerTestConfiguration(TimerTaskRunnerMock timerTaskRunnerMock) {
        this.timerTaskRunnerMock = timerTaskRunnerMock;
    }

    @Bean
    @Primary
    public TimerTaskRunner timerTaskRunner() {
        return timerTaskRunnerMock;
    }
}
