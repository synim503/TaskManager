package com.netcracker.unc.team35.task_manager.ui.notification;

import com.netcracker.unc.team35.task_manager.model.TaskModel;
import com.netcracker.unc.team35.task_manager.ui.jfx.action.tray.NotifyHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

/**
 * The class that implements the work with incoming information about new notifications
 * @author unc 21-22
 * @version 1.0
 */
public class WorkerNotification extends Thread {
    private List<NotifyHandler> listHandlersStash;
    private Boolean isClosed;

    /**
     * Instantiates a new Worker notification.
     */
    public WorkerNotification() {
        listHandlersStash = new ArrayList<>();
        this.isClosed = true;
    }

    /**
     * The main thread method, which contains the logic for displaying application notifications
     */

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            if(!listHandlersStash.isEmpty() && this.isClosed){
                NotifyHandler notify = listHandlersStash.get(0);
                listHandlersStash.remove(0);
                Platform.runLater(() -> {
                    this.isClosed = false;
                    notify.handle(new ActionEvent());
                    notify.getParentStage().setOnCloseRequest(windowEvent -> {
                        this.isClosed = true;
                    });
                });
            }
            Thread.sleep(2000);
        }
    }

    public void add(List<TaskModel> tasks){
        NotifyHandler notifyHandler = new NotifyHandler(tasks);
        listHandlersStash.add(notifyHandler);
    }
}
