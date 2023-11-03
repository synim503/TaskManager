package com.netcracker.unc.team35.task_manager.ui;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import com.netcracker.unc.team35.task_manager.ui.jfx.action.tray.AddTaskItemHandler;
import com.netcracker.unc.team35.task_manager.ui.jfx.action.tray.FindTaskItemHandler;
import javafx.application.Application;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import com.netcracker.unc.team35.task_manager.ui.notification.SubscriberNotification;

import java.io.IOException;

/**
 * Application GUI startup class
 * @author unc 21-22
 * @version 1.0
 */
public class MyTray extends Application {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

//
//        primaryStage.setOnCloseRequest(event -> {
//            try {
//                controller.perform(new ExitCommand());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

        SubscriberNotification subscriberNotification = new SubscriberNotification();
        subscriberNotification.start();

        FXTrayIcon icon = new FXTrayIcon(primaryStage, getClass().getResource("/icon/icon.png"));
        icon.addExitItem(true);

        MenuItem menuItemAddTask = new MenuItem();
        MenuItem menuItemFindTask = new MenuItem();
        MenuItem menuItemNotifi = new MenuItem();

        menuItemAddTask.setText("Добавить задачу");
        menuItemAddTask.setOnAction(new AddTaskItemHandler());

        menuItemFindTask.setText("Найти задачу");
        menuItemFindTask.setOnAction(new FindTaskItemHandler());

        icon.addMenuItem(menuItemAddTask);
        icon.addMenuItem(menuItemFindTask);
        icon.setApplicationTitle("Task Manager");
        icon.isMenuShowing();
        icon.show();

    }
}
