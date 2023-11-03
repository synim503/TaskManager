package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.TaskModel;
import com.netcracker.unc.team35.task_manager.model.scheduler.NotificationsConfig;
import com.netcracker.unc.team35.task_manager.model.scheduler.Scheduler;

import java.time.LocalDateTime;
import java.util.List;

public abstract class ScheduledTaskCommand extends AbstractCommand {
    public Object execute(ITaskRepository repository, Scheduler scheduler) throws Exception {
        TaskModel task = (TaskModel) execute(repository);
        scheduler.schedule(String.valueOf(task.getId()), calculateCommandSchedule(task));
        return task;
    }

    protected List<LocalDateTime> calculateCommandSchedule(TaskModel task) {
        return NotificationsConfig.calculateNotificationsSchedule(task.getImportance(), task.getDueDate());
    }

}
