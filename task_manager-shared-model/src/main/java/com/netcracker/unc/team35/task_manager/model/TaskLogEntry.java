package com.netcracker.unc.team35.task_manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
/*
 * Класс описывающий лог откладываний задач
 * */
@Entity
public class TaskLogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDateTime dateTime;
    private String action;

    public TaskLogEntry(LocalDateTime dateTime, String action) {
        this.dateTime = dateTime;
        this.action = action;
    }

    public TaskLogEntry() {

    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
