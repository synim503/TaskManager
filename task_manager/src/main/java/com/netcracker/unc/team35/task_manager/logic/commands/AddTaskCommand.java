package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.Importance;

import java.time.LocalDateTime;

/**
 * The class of the command to add a task to the repository
 * @author unc 21-22
 * @version 1.0
 */
public class AddTaskCommand extends ScheduledTaskCommand {
    static final String USAGE = "add taskDescription taskDueDate(dd.MM.YYYY HH:mm) importance (URGENT, HIGH, LOW)";
    private String descr;
    private LocalDateTime dueDate;
    private Importance importance;

    /**
     * Instantiates a new Add task command.
     *
     * @param descr      the description
     * @param dueDate    the due date
     * @param importance the importance
     */
    public AddTaskCommand(String descr, LocalDateTime dueDate, Importance importance) {
        this.descr = descr;
        this.dueDate = dueDate;
        this.importance = importance;
    }

    @Override
    public Object execute(ITaskRepository repository) {
        return repository.add(descr, dueDate, importance);
    }

    @Override
    public String toString() {
        return "add{" +
                "descr='" + descr + '\'' +
                ", dueDate=" + dueDate +
                ", importance=" + importance +
                '}';
    }

}
