package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class of the help call command for all commands
 * @author unc 21-22
 * @version 1.0
 */
public class HelpCommand extends AbstractCommand {
    /**
     * The Usage.
     */
    static final String USAGE = "help - print this help";
    private Map<Class<? extends AbstractCommand>, String> commandList = new LinkedHashMap<>();

    {
        commandList.put(ShowCommand.class, ShowCommand.USAGE);
        commandList.put(AddTaskCommand.class, AddTaskCommand.USAGE);
        commandList.put(CompleteTaskCommand.class, CompleteTaskCommand.USAGE);
        commandList.put(CancelTaskCommand.class, CancelTaskCommand.USAGE);
        commandList.put(PostponeTaskCommand.class, PostponeTaskCommand.USAGE);
        commandList.put(ExitCommand.class, ExitCommand.USAGE);
        commandList.put(HelpCommand.class, USAGE);
    }

    @Override
    public Object execute(ITaskRepository repository) throws Exception {

        return null;
    }

    @Override
    public String toString() {
        return usage();
    }

    @Override
    protected String usage() {
        return commandList.values().stream().collect(Collectors.joining("\n"));
    }
}
