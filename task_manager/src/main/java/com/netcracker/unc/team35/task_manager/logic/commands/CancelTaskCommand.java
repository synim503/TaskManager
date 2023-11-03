package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.TaskModel;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * The command class to update the task status to cancelled.
 * @author unc 21-22
 * @version 1.0
 */
public class CancelTaskCommand extends ScheduledTaskCommand {
    static final String USAGE = "cancel taskId";
    private String id;

    @Override
    public String toString() {
        return "cancel{" + id + '}';
    }

    @Override
    protected String usage() {
        return USAGE;
    }

    /**
     * Instantiates a new Cancel task command.
     *
     * @param id the id
     */
    public CancelTaskCommand(String id) {
        this.id = id;
    }

    @Override
    public Object execute(ITaskRepository repository) {
        repository.cancel(id);
        return repository.findById(id);
    }

    @Override
    protected List<LocalDateTime> calculateCommandSchedule(TaskModel task) {
        return Collections.emptyList();
    }
}
