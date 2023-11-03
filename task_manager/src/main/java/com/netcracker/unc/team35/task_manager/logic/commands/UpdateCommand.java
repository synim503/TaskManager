package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.model.TaskUpdate;

import java.time.LocalDateTime;

/**
 * The class describes the update command model
 * @author unc 21-22
 * @version 1.0
 */

public class UpdateCommand extends ScheduledTaskCommand {
    private String id;
    private LocalDateTime dueDate;
    private Importance importance;
    private String description;

    /**
     * Instantiates a new Update command.
     *
     * @param id          the id
     * @param description the description
     * @param dueDate     the due date
     * @param importance  the importance
     */
    public UpdateCommand(String id, String description, LocalDateTime dueDate, Importance importance) {
        this.id = id;
        this.description = description;
        this.dueDate = dueDate;
        this.importance = importance;
    }

    @Override
    public Object execute(final ITaskRepository repository) throws Exception {
        repository.update(id, new TaskUpdate()
                .dueDate(dueDate)
                .description(description)
                .importance(importance));
        return repository.findById(id);
    }

    @Override
    public boolean isSilent() {
        return false;
    }

    @Override
    protected String usage() {
        return "TBD";
    }

}
