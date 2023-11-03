package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.scheduler.Scheduler;

/**
 * The type Abstract command.
 * @author unc 21-22
 * @version 1.0
 */
public abstract class AbstractCommand implements ICommand {
    /**
     * {@link ICommand#execute(ITaskRepository)}
     */
    @Override
    public abstract Object execute(ITaskRepository repository) throws Exception;


    /**
     * {@link ICommand#execute(ITaskRepository, Scheduler)}}
     */
    @Override
    public Object execute(ITaskRepository repository, Scheduler scheduler) throws Exception {
        return execute(repository);
    }

    /**
     * {@link ICommand#isSilent()}}
     */
    @Override
    public boolean isSilent() {
        return ICommand.super.isSilent();
    }

    /**
     * Usage string.
     *
     * @return the string
     */

    protected String usage() {
        return null;
    }

}
