package com.netcracker.unc.team35.task_manager.ui.jfx.action.tray;

import com.netcracker.unc.team35.task_manager.ui.jfx.controller.FindTaskFormController;
import javafx.fxml.FXMLLoader;


/**
 * The class opens the "Find Task" window
 */
public class FindTaskItemHandler extends ClickMenuItemHandler {
    @Override
    public void setController(FXMLLoader loader) {
        loader.setController(new FindTaskFormController());
    }

    @Override
    int getWidth() {
        return 1100;
    }

    @Override
    int getHeight() {
        return 650;
    }

    @Override
    String getPathFxml() {
        return "/fxml/findTask.fxml";
    }
}
