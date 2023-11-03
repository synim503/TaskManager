package com.netcracker.unc.team35.task_manager.logic;

import com.netcracker.unc.team35.task_manager.logic.commands.ICommand;
import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.scheduler.Scheduler;
import com.netcracker.unc.team35.task_manager.view.TasksOutput;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Commands controller class
 *
 * @author unc 21-22
 * @version 1.0
 */
public class Controller {
    private ITaskRepository repository;
    private Scheduler scheduler;
    private TasksOutput view;

    /**
     * Instantiates a new Controller.
     *
     * @param repository the repository
     * @param view       the view
     * @param scheduler  the scheduler
     */
    public Controller(final ITaskRepository repository, TasksOutput view, Scheduler scheduler) {
        this.repository = repository;
        this.view = view;
        this.scheduler = scheduler;
    }

    /**
     * Perform the command
     *
     * @param command the command
     * @return the object
     * @throws Exception the exception
     */
    public Object perform(ICommand command) throws Exception {
        Object result = command.execute(repository, scheduler);
        if (!command.isSilent())
            view.update(repository.getAllTasks());
        else if (view != null) view.out(command + "\n");
        return result;
    }

    /**
     * Gets view.
     *
     * @return the view
     */
    public TasksOutput getView() {
        return view;
    }
}
