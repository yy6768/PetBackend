package com.example.petbackend.consumer.utils;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DynamicExamScheduler {

    private final TaskScheduler taskScheduler;

    public DynamicExamScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void scheduleTask(Runnable task, Instant startTime) {
        taskScheduler.schedule(task, startTime);
    }
}
