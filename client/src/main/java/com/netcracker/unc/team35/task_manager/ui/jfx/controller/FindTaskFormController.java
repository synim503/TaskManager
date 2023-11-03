package com.netcracker.unc.team35.task_manager.ui.jfx.controller;

import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.model.Status;
import com.netcracker.unc.team35.task_manager.ui.jfx.action.form.FindTaskButtonHandler;
import com.netcracker.unc.team35.task_manager.ui.jfx.model.TaskModel;
import com.netcracker.unc.team35.task_manager.ui.jfx.action.tray.EditTaskItemHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * The controller class that describes the behavior of elements of the task search window
 * @author unc 21-22
 * @version 1.0
 */
public class FindTaskFormController {

    @FXML
    private Button buttonFindTask;

    @FXML
    private Button buttonReset;

    @FXML
    private ToggleGroup radioButtonGroup;

    @FXML
    private DatePicker datePeriodFrom;

    @FXML
    private DatePicker datePeriodTo;

    @FXML
    private TableColumn<TaskModel, LocalDateTime> tableData;

    @FXML
    private TableColumn<TaskModel, String> tableDescr;

    @FXML
    private TableColumn<TaskModel, Importance> tableImportance;

    @FXML
    private TableColumn<TaskModel, Status> tableStatus;

    @FXML
    private TableView<TaskModel> tableTasks;

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
        tableDescr.setCellValueFactory(new PropertyValueFactory<>("descr"));
        tableData.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableImportance.setCellValueFactory(new PropertyValueFactory<>("importance"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        buttonFindTask.setOnAction(
                new FindTaskButtonHandler(
                        tableTasks,
                        radioButtonGroup,
                        textAreaDescr,
                        datePeriodFrom,
                        datePeriodTo
                )
        );

        tableTasks.setRowFactory(tv -> {
            TableRow<TaskModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TaskModel rowData = row.getItem();
                    new EditTaskItemHandler(rowData).handle(new ActionEvent());
                }
            });
            return row;
        });
        buttonReset.setOnAction(event -> {
            textAreaDescr.setText("");
            radioButtonGroup.selectToggle(null);
            datePeriodFrom.setValue(null);
            datePeriodTo.setValue(null);
        });
    }

    /**
     * Gets key event event handler.
     *
     * @param maxSymb    the maximum number of characters
     * @param maxTimeInt the max time int
     */
    static EventHandler<KeyEvent> getKeyEventEventHandler(int maxSymb, int maxTimeInt) {
        return arg0 -> {
            Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");

            TextField textField = (TextField) arg0.getSource();

            if (textField.getText().length() >= maxSymb) {
                arg0.consume();
            }

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!p.matcher(newValue).matches()) {
                    textField.setText(oldValue);
                }
                if (!textField.getText().isEmpty() && Integer.parseInt(textField.getText()) > maxTimeInt) {
                    textField.setText(String.valueOf(maxTimeInt));
                }
            });
        };
    }

}
