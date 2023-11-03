package com.netcracker.unc.team35.task_manager.ui.jfx.model;

import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.model.Status;

import java.time.LocalDateTime;

/**
 * The class describing the task model for TableView
 */
public class TaskModel {
    private String id;
    private String descr;
    private LocalDateTime date;
    private Importance importance;
    private Status status;

    /**
     * Instantiates a new Task model.
     *
     * @param id         the id
     * @param descr      the description
     * @param date       the date
     * @param importance the importance
     * @param status     the status
     */
    public TaskModel(String id, String descr, LocalDateTime date, Importance importance, Status status) {
        this.id = id;
        this.descr = descr;
        this.date = date;
        this.importance = importance;
        this.status = status;
    }

    /**
     * Gets description.
     *
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Sets description.
     *
     * @param descr the descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Gets importance.
     *
     * @return the importance
     */
    public Importance getImportance() {
        return importance;
    }

    /**
     * Sets importance.
     *
     * @param importance the importance
     */
    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }
}
