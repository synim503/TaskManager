package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.TaskModel;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * The command class to update the task status to completed
 * @author unc 21-22
 * @version 1.0
 */
public class CompleteTaskCommand extends ScheduledTaskCommand {
    static final String USAGE = "complete taskId";
    private String id;

    @Override
    public String toString() {
        return "complete{" + id + '}';
    }

    @Override
    protected String usage() {
        return USAGE;
    }

    /**
     * Instantiates a new Complete task command.
     *
     * @param id the id
     */
    public CompleteTaskCommand(String id) {
        this.id = id;
    }

    @Override
    public Object execute(ITaskRepository repository) {
        repository.complete(id);
        return repository.findById(id);
    }

    @Override
    protected List<LocalDateTime> calculateCommandSchedule(TaskModel task) {
        return Collections.emptyList();
    }
}
