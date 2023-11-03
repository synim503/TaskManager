package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.*;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;

/**
 * The class describes a model for delayed execution time
 * @author unc 21-22
 * @version 1.0
 */
public class PostponeTaskCommand extends ScheduledTaskCommand {
    static final String USAGE = "postpone taskId period";
    private String id;
    private TemporalAmount plus;

    /**
     * Instantiates a new Postpone task command.
     *
     * @param id   the id
     * @param plus the time for which the task should be postponed
     */
    public PostponeTaskCommand(String id, TemporalAmount plus) {

        this.id = id;
        this.plus = plus;
    }

    @Override
    public Object execute(ITaskRepository repository) throws Exception {
        TaskModel task = (TaskModel) repository.findById(id);
        LocalDateTime dueDate = task.getDueDate();
        List<TaskLogEntry> taskLog = task.getTaskLog();
        Importance importance = task.getImportance();
        if (taskLog.size() >= importance.getAllowedPostpones())
            throw new Exception("too many postpones for tasks with importance:" + importance);
        if (importance.getPostponingPeriod().equals(plus))
            throw new Exception("impossible to postpone task with importance to period more than " + importance.getPostponingPeriod());

        TaskUpdate taskUpdate = new TaskUpdate().dueDate(dueDate.plus(plus));
        taskLog.add(new TaskLogEntry(dueDate.plus(plus),"Postpone"));
        taskUpdate.setLog(taskLog);
        repository.update(id, new TaskUpdate().dueDate(dueDate.plus(plus)));
        return task;
    }

    @Override
    protected String usage() {
        return USAGE;
    }

}
