package com.netcracker.unc.team35.task_manager.model.scheduler;

import java.time.LocalDateTime;

public class TaskNotificationInfo {
    private final String taskId;
    private final String descr;
    private final LocalDateTime dueDate;
    private final String importance;

    public TaskNotificationInfo(String taskId, String descr, LocalDateTime dueDate, String importance) {
        this.taskId = taskId;
        this.descr = descr;
        this.dueDate = dueDate;
        this.importance = importance;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getDescr() {
        return descr;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getImportance() {
        return importance;
    }
}
