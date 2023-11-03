package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;

/**
 * Exit command class
 *  @author unc 21-22
 *  @version 1.0
 */
public class ExitCommand extends AbstractCommand {
    static final String USAGE = "exit";

    /**
     * Updates the repository when the command is executed
     * @param repository
     * @return
     * @throws Exception
     */
    @Override
    public Object execute(final ITaskRepository repository) throws Exception {
        repository.flush();
        System.exit(0);
        return null;
    }

    @Override
    public boolean isSilent() {
        return false;
    }

    @Override
    protected String usage() {
        return USAGE;
    }
}
