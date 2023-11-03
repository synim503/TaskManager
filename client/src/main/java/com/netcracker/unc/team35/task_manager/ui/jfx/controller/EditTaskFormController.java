package com.netcracker.unc.team35.task_manager.ui.jfx.controller;

import com.netcracker.unc.team35.task_manager.ui.jfx.action.form.EditTaskDoubleClickHandler;
import com.netcracker.unc.team35.task_manager.ui.jfx.model.TaskModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import static com.netcracker.unc.team35.task_manager.ui.jfx.controller.FindTaskFormController.getKeyEventEventHandler;


/**
 * The controller class that describes the behavior of the elements of the task change window
 * @author unc 21-22
 * @version 1.0
 */
public class EditTaskFormController {
    private TaskModel rowData;
    private Stage stage;

    /**
     * Sets controller.
     *
     * @param rowData the row data
     * @param stage   the stage
     */
    public void setController(TaskModel rowData, Stage stage) {
        this.rowData = rowData;
        this.stage = stage;
    }

    @FXML
    private Button buttonAddTask;

    @FXML
    private DatePicker dateDueDate;

    @FXML
    private ToggleGroup radioButtonGroup;

    @FXML
    private TextArea textAreaDescr;

    @FXML
    private TextField textHours;

    @FXML
    private TextField textMinutes;

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
        fillFormFields();
        textMinutes.addEventFilter(KeyEvent.KEY_TYPED, filterTimeTextField(2, 59));
        textHours.addEventFilter(KeyEvent.KEY_TYPED, filterTimeTextField(2, 23));
        buttonAddTask.setOnAction(
                new EditTaskDoubleClickHandler(
                        rowData.getId(),
                        radioButtonGroup,
                        dateDueDate,
                        textAreaDescr,
                        textHours,
                        textMinutes,
                        this.rowData,
                        this.stage));
    }

    /**
     * Filter time text field.
     *
     * @param maxSymb    the max symb
     * @param maxTimeInt the max time int
     * @return the event handler
     */
    public EventHandler<KeyEvent> filterTimeTextField(int maxSymb, int maxTimeInt) {
        return getKeyEventEventHandler(maxSymb, maxTimeInt);
    }

    private void fillFormFields() {

        textAreaDescr.setText(rowData.getDescr());
        dateDueDate.setValue(rowData.getDate().toLocalDate());
        textHours.setText(String.valueOf(rowData.getDate().getHour()));
        textMinutes.setText(String.valueOf(rowData.getDate().getMinute()));
    }

}
