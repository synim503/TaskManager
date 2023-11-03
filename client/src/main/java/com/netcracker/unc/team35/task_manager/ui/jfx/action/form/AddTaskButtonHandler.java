package com.netcracker.unc.team35.task_manager.ui.jfx.action.form;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.netcracker.unc.team35.task_manager.model.Importance;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The class containing the logic, the window application element, the <strong>Add task</strong> button"
 * @author unc 21-22
 * @version 1.0
 */
public class AddTaskButtonHandler implements EventHandler<ActionEvent> {

    private ToggleGroup radioButtonGroup;
    private DatePicker datePicker;
    private TextArea descrArea;
    private TextField hoursField;
    private TextField minutesField;

    private String taskDescription;
    private Importance importance;
    private LocalDateTime dueDate;

    private List<String> validationMessages = new ArrayList<>();

    /**
     * Instantiates a new Add task handler.
     *
     * @param radioButtonGroup the radio button importance
     * @param datePicker       the due date
     * @param descrArea        the description
     * @param hoursField       the hours field
     * @param minutesField     the minutes field
     */
    public AddTaskButtonHandler(ToggleGroup radioButtonGroup,
                                DatePicker datePicker,
                                TextArea descrArea,
                                TextField hoursField,
                                TextField minutesField) {
        this.radioButtonGroup = radioButtonGroup;
        this.datePicker = datePicker;
        this.descrArea = descrArea;
        this.hoursField = hoursField;
        this.minutesField = minutesField;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        parseInput();
        if (!validationMessages.isEmpty()) {
            alertError(String.join("\n", validationMessages));
            return;
        }

        try {
            if(addTask(taskDescription,dueDate,importance)){
                alertSuccessful("Задача запланирована.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void parseInput() {
        validationMessages.clear();
        parseDescription();
        parseImportance();
        parseDueDate();

    }
    /*
     * Method for validating task importance
     * */
    private void parseImportance() {
        if (radioButtonGroup.getSelectedToggle() == null) {
            validationMessages.add("Не указана важность.");
        } else {
            this.importance = Importance.valueOf(radioButtonGroup.getSelectedToggle().toString().split("'")[1]);
        }
    }
    /*
     * Method for validating the task description
     * */
    private void parseDescription() {
        if ((descrArea.getText().isEmpty())) {
            validationMessages.add("Не задано описание задачи.");
        } else {
            this.taskDescription = descrArea.getText();
        }
    }

    /**
     * Method for validating the date of task execution
     */
    private void parseDueDate() {
        if (datePicker.getValue() == null) {
            validationMessages.add("Не указана дата.");
        } else {
            if (minutesField.getText().isEmpty() || hoursField.getText().isEmpty()) {
                validationMessages.add("Не указано время.");
            } else {
                LocalDate localDate = datePicker.getValue();
                LocalDateTime localDateTime = localDate.atTime(Integer.parseInt(hoursField.getText()), Integer.parseInt(minutesField.getText()));
                if (localDateTime.compareTo(LocalDateTime.now()) < 0) {
                    validationMessages.add("Дата не может быть в прошлом!");
                } else {
                    this.dueDate = localDateTime;
                }
            }
        }
    }

    /**
     * Method calling the error window
     * @param textMessage
     */
    private void alertError(String textMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(textMessage);
        alert.showAndWait();
    }

    /**
     * Method calling the successful execution window
     * @param textMessage
     */
    private void alertSuccessful(String textMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно");
        alert.setHeaderText(null);
        alert.setContentText(textMessage);
        alert.showAndWait();
        cleanUp();
    }

    private void cleanUp() {
        hoursField.clear();
        minutesField.clear();
        descrArea.clear();
        datePicker.setValue(null);
    }

    /**
     * The method of adding a task by rest api
     *
     * @param descr      the descr
     * @param dueDate    the due date
     * @param importance the importance
     * @return the boolean
     */
    @SneakyThrows
    public boolean addTask(String descr, LocalDateTime dueDate, Importance importance){

        JSONObject object = new JSONObject();
        object.put("descr", descr);
        object.put("dueDate", dueDate);
        object.put("importance", importance);

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:9999/api/v1/tasks/add")
                .header("Content-Type", "application/json")
                .body(object)
                .asString();
        return true;
    }
}
