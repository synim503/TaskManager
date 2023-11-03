package com.netcracker.unc.team35.task_manager;

import com.netcracker.unc.team35.task_manager.logic.CommandFactory;
import com.netcracker.unc.team35.task_manager.logic.Controller;
import com.netcracker.unc.team35.task_manager.logic.commands.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;
@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
public class TasksManager implements CommandLineRunner {

    private Controller controller;
    @Autowired
    public TasksManager(Controller controller) {
        this.controller = controller;
    }

    public void executeCommand(String commandStr) throws Exception {
        ICommand command = CommandFactory.resolve(commandStr);
        controller.perform(command);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(TasksManager.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        executeCommand("?");
        do executeCommand(scanner.nextLine().trim()); while (true);

    }
}
