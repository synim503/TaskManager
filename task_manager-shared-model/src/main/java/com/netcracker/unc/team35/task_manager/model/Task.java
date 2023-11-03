package com.netcracker.unc.team35.task_manager.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Task.
 * @author unc 21-22
 * @version 1.0
 */
public interface Task {
    /**
     * Gets description.
     *
     * @return the descr
     */
    String getDescr();

    /**
     * Sets description.
     *
     * @param descr the descr
     */
    void setDescr(String descr);

    /**
     * Gets due date.
     *
     * @return the due date
     */
    LocalDateTime getDueDate();

    /**
     * Sets due date.
     *
     * @param dueDate the due date
     */
    void setDueDate(LocalDateTime dueDate);

    /**
     * Gets importance.
     *
     * @return the importance
     */
    Importance getImportance();

    /**
     * Sets importance.
     *
     * @param importance the importance
     */
    void setImportance(Importance importance);

    /**
     * Gets id.
     *
     * @return the id
     */
    Long getId();

    /**
     * Gets status.
     *
     * @return the status
     */
    Status getStatus();

    /**
     * Sets status.
     *
     * @param status the status
     */
    void setStatus(Status status);

    /**
     * Sets id.
     *
     * @param id the id
     */
    void setId(Long id);

    List<TaskLogEntry> getTaskLog();

     void setTaskLog(List<TaskLogEntry> taskLog);
}
