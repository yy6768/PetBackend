package com.example.petbackend.consumer.utils;

import com.example.petbackend.consumer.WebSocketServer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Component
public class DynamicExamScheduler {

    private final TaskScheduler scheduler;
    private ScheduledFuture<?> scheduledFuture;

    public DynamicExamScheduler() {
        this.scheduler = new ThreadPoolTaskScheduler();
        ((ThreadPoolTaskScheduler) scheduler).initialize();
    }

    public void scheduleEndExam(Instant endTime) {
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(false);
        }
        scheduledFuture = scheduler.schedule(this::endExam, endTime);
    }

    private void endExam() {

    }
}
