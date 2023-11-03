package com.netcracker.unc.team35.task_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The class describing the task
 * @author unc 21-22
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)

@Entity
@Table(name = "tasks")
public class TaskModel implements Task, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 6, nullable = false)
    private Long id;

    private String descr;

    private LocalDateTime dueDate;

    private Importance importance;

    @OneToMany( cascade = CascadeType.ALL)
    private List<TaskLogEntry> taskLog;

    /**
     * Instantiates a new Task model.
     */
    public TaskModel(){}

    /**
     * Instantiates a new Task model.
     *
     * @param descr      the description
     * @param dueDate    the due date
     * @param importance the importance
     * @param status     the status
     */
    public TaskModel(@JsonProperty("descr") String descr,
                     @JsonProperty("dueDate") LocalDateTime dueDate,
                     @JsonProperty("importance") Importance importance,
                     @JsonProperty("status") Status status) {
        this.descr = descr;
        this.dueDate = dueDate;
        this.importance = importance;
        this.status = status;
        this.taskLog = new ArrayList<>();
    }

    private Status status;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    /**
     * Gets task log.
     *
     * @return the task log
     */
    public List<TaskLogEntry> getTaskLog() {
        return taskLog;
    }

    /**
     * Sets task log.
     *
     * @param taskLog the task log
     */
    public void setTaskLog(List<TaskLogEntry> taskLog) {
        this.taskLog = taskLog;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
