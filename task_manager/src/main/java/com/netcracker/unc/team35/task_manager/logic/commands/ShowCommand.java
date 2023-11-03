package com.netcracker.unc.team35.task_manager.logic.commands;

import com.netcracker.unc.team35.task_manager.model.ITaskRepository;
import com.netcracker.unc.team35.task_manager.model.Task;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * The command class that displays the entire task list
 * @author unc 21-22
 * @version 1.0
 */
public class ShowCommand extends AbstractCommand {

    static final String USAGE = "show ...";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public Object execute(ITaskRepository repository) throws Exception {
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("ID", "Описание", "Дата", "Статус", "Важность").setTextAlignment(TextAlignment.CENTER);
        Collection<Task> tasks = repository.getAllTasks();
        for (Task task : tasks) {
            at.addRule();
            at.addRow(task.getId(), task.getDescr(),
                            task.getDueDate().format(DATE_TIME_FORMATTER),
                            task.getStatus(),
                            task.getImportance())
                    .setTextAlignment(TextAlignment.CENTER);
        }
        at.addRule();
        at.setPaddingLeftRight(1);
        System.out.println(at.render());
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
