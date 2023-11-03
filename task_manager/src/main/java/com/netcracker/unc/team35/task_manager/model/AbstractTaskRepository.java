package com.netcracker.unc.team35.task_manager.model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class describing methods of working with the database
 * @author unc 21-22
 * @version 1.0
 */
public abstract class AbstractTaskRepository implements ITaskRepository {
    /**
     * The collection tasks.
     */
    protected Map<String, Task> tasks = new HashMap<>();

    /**
     * Get task collection
     */
    @Override
    public abstract Collection<Task> getAllTasks();

    /**
     * Get the task by id
     * @param id the id
     * @return Task
     */
    @Override
    public Task findById(String id) {
        return tasks.get(id);
    }

    /**
     * Add a task to the collection
     * @param descr      the description
     * @param dateTime   the date time
     * @param importance the importance
     * @return
     */
    @Override
    public Task add(String descr, LocalDateTime dateTime, Importance importance) {
        TaskModel task = new TaskModel(descr, dateTime, importance, Status.AWAITING);

        tasks.put(task.getId().toString(), task);
        return task;
    }

    /**
     * Next id string.
     *
     * @return the string
     */
    protected abstract String nextId();

    /**
     * Change task status to canceled
     * @param id the id
     */
    @Override
    public void cancel(String id) {
        update(id, new TaskUpdate().status(Status.CANCELLED));
    }

    /**
     * Change the task status to completed
     * @param id the id
     */
    @Override
    public void complete(String id) {
        update(id, new TaskUpdate().status(Status.COMPLETED));
    }

    /**
     * Method for updating a task in a collection
     * @param id         the id
     * @param taskUpdate the task update
     */
    @Override
    public void update(String id, TaskUpdate taskUpdate) {
        final Task byId = findById(id);
        final Status status = taskUpdate.getStatus();
        if (status != null)
            byId.setStatus(status);
        final LocalDateTime dueDate = taskUpdate.getDueDate();
        if (dueDate != null)
            byId.setDueDate(dueDate);
        final Importance importance = taskUpdate.getImportance();
        if (importance != null)
            byId.setImportance(importance);
        String descr = taskUpdate.getDescr();
        if (descr != null)
            byId.setDescr(descr);

    }

    /**
     * Write the current state of tasks from the collection to the file repository
     * @throws IOException
     */
    @Override
    public abstract void flush() throws IOException;
}
