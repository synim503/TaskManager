package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.querydsl.core.types.Predicate;
/**
 * The class of the search command by predicate
 *  @author unc 21-22
 *  @version 1.0
 */
public class FindCommand extends AbstractCommand {
    private Predicate predicate;

    /**
     * Instantiates a new Find command.
     *
     * @param predicate the predicate
     */
    public FindCommand(com.querydsl.core.types.Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public Object execute(ITaskRepository repository) throws Exception {
        return repository.findByPredicate(predicate);
    }
}
