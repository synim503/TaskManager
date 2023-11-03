package com.netcracker.unc.team35.task_manager.ui.jfx.action.tray;

import com.netcracker.unc.team35.task_manager.ui.jfx.controller.EditTaskFormController;
import com.netcracker.unc.team35.task_manager.ui.jfx.model.TaskModel;
import javafx.fxml.FXMLLoader;

/**
 * The class opens the "Change task" window
 * @author unc 21-22
 * @version 1.0
 */
public class EditTaskItemHandler extends ClickMenuItemHandler {
    private TaskModel rowData;

    /**
     * Instantiates a new Edit task item handler.
     *
     * @param rowData the row data
     */
    public EditTaskItemHandler(TaskModel rowData) {
        this.rowData = rowData;
    }

    @Override
    public void setController(FXMLLoader loader) {
        EditTaskFormController editTaskFormController = new EditTaskFormController();
        editTaskFormController.setController(rowData,getStage());
        loader.setController(editTaskFormController);
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
        return "/fxml/editTask.fxml";
    }
}

