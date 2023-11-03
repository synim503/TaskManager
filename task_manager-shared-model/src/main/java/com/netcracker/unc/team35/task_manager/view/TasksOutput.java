package com.netcracker.unc.team35.task_manager.view;

import com.netcracker.unc.team35.task_manager.model.Task;
import com.netcracker.unc.team35.task_manager.model.TaskModel;

import java.io.IOException;
import java.util.Collection;

public interface TasksOutput {
    void update(Collection<Task> allTasks) throws IOException;

    void out(String s) throws IOException;
}
