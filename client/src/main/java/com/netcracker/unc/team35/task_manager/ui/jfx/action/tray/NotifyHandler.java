package com.netcracker.unc.team35.task_manager.ui.jfx.action.tray;
import com.netcracker.unc.team35.task_manager.model.TaskModel;
import com.netcracker.unc.team35.task_manager.ui.jfx.controller.NotifyController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.List;

/**
 * The class opens the notification window
 * @author unc 21-22
 * @version 1.0
 */
public class NotifyHandler extends ClickMenuItemHandler {
    private List<TaskModel> tasks;

    /**
     * Instantiates a new Notify handler.
     *
     * @param tasks the tasks
     */
    public NotifyHandler(List<TaskModel> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void setController(FXMLLoader loader) {
        NotifyController notifyController = new NotifyController();
        notifyController.setController(tasks, getStage());
        loader.setController(notifyController);
    }
    @Override
    int getWidth() {
        return 800;
    }

    @Override
    int getHeight() {
        return 300;
    }

    @Override
    String getPathFxml() {
        return "/fxml/notify.fxml";
    }

    /**
     * Get parent stage stage.
     *
     * @return the stage
     */
    public Stage getParentStage(){
        return getStage();
    }

}
