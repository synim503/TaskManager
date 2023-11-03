package com.netcracker.unc.team35.task_manager.ui.jfx.controller;

import com.netcracker.unc.team35.task_manager.ui.jfx.action.form.AddTaskButtonHandler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import static com.netcracker.unc.team35.task_manager.ui.jfx.controller.FindTaskFormController.getKeyEventEventHandler;

/**
 * The controller class describing the behavior of the elements of the window for adding a new task
 * @author unc 21-22
 * @version 1.0
 */
public class AddTaskFormController {

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
        textMinutes.addEventFilter(KeyEvent.KEY_TYPED, filterTimeTextField(2, 59));
        textHours.addEventFilter(KeyEvent.KEY_TYPED, filterTimeTextField(2, 23));
        buttonAddTask.setOnAction(
                new AddTaskButtonHandler(
                        radioButtonGroup, dateDueDate, textAreaDescr, textHours, textMinutes));
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

}
