package com.netcracker.unc.team35.task_manager.ui.jfx.action.tray;

import com.netcracker.unc.team35.task_manager.ui.jfx.controller.AddTaskFormController;
import javafx.fxml.FXMLLoader;

/**
 * The class opens the "Add task" window
 * @author unc 21-22
 * @version 1.0
 */
public class AddTaskItemHandler extends ClickMenuItemHandler {

    @Override
    public void setController(FXMLLoader loader) {
        loader.setController(new AddTaskFormController());
    }

    @Override
    int getWidth() {
        return 800;
    }

    @Override
    int getHeight() {
        return 320;
    }

    @Override
    String getPathFxml() {
        return "/fxml/addTask.fxml";
    }
}
