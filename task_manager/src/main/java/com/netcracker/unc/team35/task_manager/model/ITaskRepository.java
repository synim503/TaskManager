package com.netcracker.unc.team35.task_manager.model;

import com.querydsl.core.types.Predicate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


/**
 * The interface task repository.
 */
public interface ITaskRepository {
    /**
     * Gets all tasks.
     *
     * @return the all tasks
     */
    Collection<Task> getAllTasks();

    /**
     * Find task by id.
     *
     * @param id the id
     * @return the task
     */
    Task findById(String id);

    /**
     * Add task.
     *
     * @param descr      the description
     * @param dateTime   the date time
     * @param importance the importance
     * @return the task
     */
    Task add(String descr, LocalDateTime dateTime, Importance importance);

    /**
     * Cancel.
     *
     * @param id the id
     */
    void cancel(String id);

    /**
     * Complete.
     *
     * @param id the id
     */
    void complete(String id);


    /**
     * Update.
     *
     * @param id         the id
     * @param taskUpdate the task update
     */
    void update(String id, TaskUpdate taskUpdate);

    /**
     * Flush.
     *
     * @throws IOException the io exception
     */
    void flush() throws IOException;

    /**
     * Find by predicate list.
     *
     * @param predicate the predicate
     * @return the list
     */
    List<Task> findByPredicate(Predicate predicate);
}
