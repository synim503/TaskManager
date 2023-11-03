package com.netcracker.unc.team35.task_manager.logic;

import com.netcracker.unc.team35.task_manager.logic.commands.*;
import com.netcracker.unc.team35.task_manager.model.Importance;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * A class that handles user commands given from the console
 * @author unc 21-22
 * @version 1.0
 */
public class CommandFactory {

    /**
     * Resolve command.
     *
     * @param commandString the command string
     * @return the command
     */
    public static ICommand resolve(String commandString) {
        if (commandString.startsWith("add")) {
            String[] parseStrCommand = commandString.split(" ");

            StringBuilder descr = new StringBuilder();
            String importance = parseStrCommand[parseStrCommand.length-1];

            LocalDateTime dueDate = LocalDateTime.parse(
                parseStrCommand[parseStrCommand.length-3]+" "+parseStrCommand[parseStrCommand.length-2],
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            for (int i = 1; i < parseStrCommand.length-3 ; i++) {
                descr.append(parseStrCommand[i]).append(" ");
            }
            return new AddTaskCommand(descr.toString().trim(), dueDate, Importance.valueOf(importance));
        }
        else if (commandString.startsWith("postpone")) {
            String[] parseStrCommand = commandString.split(" ");

            String id = parseStrCommand[1];
            Duration plus = Duration.parse(parseStrCommand[2]);
            return new PostponeTaskCommand(id, plus);
        }
        else if (commandString.startsWith("complete")) {
            String id = commandString.split(" ")[1];
            return new CompleteTaskCommand(id);
        }
        else if (commandString.startsWith("cancel")) {
            String id = commandString.split(" ")[1];
            return new CancelTaskCommand(id);
        }
        else if (commandString.startsWith("exit")) {
            return new ExitCommand();
        }
        else if (commandString.startsWith("show")) {
            return new ShowCommand();
        } else
            return new HelpCommand();
        }
    }