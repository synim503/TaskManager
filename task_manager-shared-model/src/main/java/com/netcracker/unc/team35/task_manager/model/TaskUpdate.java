package com.netcracker.unc.team35.task_manager.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The class describes the object for updating the task
 * @author unc 21-22
 * @version 1.0
 */
public class TaskUpdate {
    private Status status;
    private LocalDateTime dueDate;
    private Importance importance;
    private String descr;
    private List<TaskLogEntry> log;
    /**
     * Gets status.
     *
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Gets due date.
     *
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Gets importance.
     *
     */
    public Importance getImportance() {
        return importance;
    }

    /**
     * Status task update.
     *
     * @param status the status
     */
    public TaskUpdate status(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Due date task update.
     *
     * @param plus the plus
     */
    public TaskUpdate dueDate(LocalDateTime plus) {
        dueDate = plus;
        return this;
    }

    /**
     * Importance task update.
     *
     * @param importance the importance
     */
    public TaskUpdate importance(Importance importance) {
        this.importance = importance;
        return this;
    }

    /**
     * Description task update.
     *
     * @param descr the descr
     */
    public TaskUpdate description(String descr) {
        this.descr = descr;
        return this;
    }

    /**
     * Gets description.
     */
    public String getDescr() {
        return descr;
    }

    public List<TaskLogEntry> getLog() {
        return log;
    }

    public void setLog(List<TaskLogEntry> log) {
        this.log = log;
    }
}
