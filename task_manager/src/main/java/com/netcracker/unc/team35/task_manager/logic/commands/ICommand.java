package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.scheduler.Scheduler;

/**
 * The interface Command.
 * @author unc 21-22
 * @version 1.0
 */
public interface ICommand {
    /**
     * Execute object.
     *
     * @param repository the repository
     * @return the object
     * @throws Exception the exception
     */
    Object execute(ITaskRepository repository) throws Exception;

    /**
     * Execute object.
     *
     * @param repository the repository
     * @param scheduler  the scheduler
     * @return the object
     * @throws Exception the exception
     */
    Object execute(ITaskRepository repository, Scheduler scheduler) throws Exception;

    /**
     * Is silent boolean.
     *
     * @return the boolean
     */
    default boolean isSilent() {
        return true;
    }

}
