package com.netcracker.unc.team35.task_manager.ui.jfx.action.form;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.ui.jfx.model.TaskModel;
import com.netcracker.unc.team35.task_manager.ui.network.WebClientApi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A class that contains the logic for editing a task
 * @author unc 21-22
 * @version 1.0
 */
public class EditTaskDoubleClickHandler implements EventHandler<ActionEvent> {
    private String taskId;
    private ToggleGroup radioButtonGroup;
    private DatePicker dateDueDate;
    private TextArea textAreaDescr;
    private TextField textHours;
    private TextField textMinutes;
    private TaskModel rowData;
    private Stage stage;

    private String textAreaDescrValid;
    private Importance importanceValid;
    private LocalDateTime dueDateValid;

    /**
     * Instantiates a new Edit handler.
     *
     * @param id               the id
     * @param radioButtonGroup the radio button group
     * @param dateDueDate      the date due date
     * @param textAreaDescr    the text area descr
     * @param textHours        the text hours
     * @param textMinutes      the text minutes
     * @param rowData          the row data
     * @param stage            the stage
     */
    public EditTaskDoubleClickHandler(String id,
                                      ToggleGroup radioButtonGroup,
                                      DatePicker dateDueDate,
                                      TextArea textAreaDescr,
                                      TextField textHours,
                                      TextField textMinutes,
                                      TaskModel rowData,
                                      Stage stage
    ) {
        this.taskId = id;
        this.radioButtonGroup = radioButtonGroup;
        this.dateDueDate = dateDueDate;
        this.textAreaDescr = textAreaDescr;
        this.textHours = textHours;
        this.textMinutes = textMinutes;
        this.rowData = rowData;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        List<String> validationMessages = validateFormInput();
        if(excludeOverwritingDuplicate()){
            alertError("Задача осталась прежней");
            return;
        }
        if (!validationMessages.isEmpty()) {
            alertError(String.join("\n", validationMessages));
        } else {
            try {
                updateTaskWithParams(buildParams());
                alertSuccessful("Задача обновлена");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    private String updateTaskWithParams(Map<String, Object> values){
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put(WebClientApi.REST_API_URL + "/api/v1/tasks/update")
                .queryString(values)
                .asString();
        return response.getBody();
    }

    private Map<String, Object> buildParams() {
        Map map = new HashMap<String, Object>();

        map.put("id", this.taskId != null ? taskId : "");
        map.put("descr", this.textAreaDescrValid != null ? textAreaDescrValid : "");
        map.put("dueDate", this.dueDateValid != null ? this.dueDateValid.toString() : "");
        map.put("importance", this.importanceValid != null ? this.importanceValid.toString() : "");

        return map;
    }

    /**
     * Method for validating transmitted data
     * @return listException
     */
    private List<String> validateFormInput() {
        List<String> listException = new ArrayList<String>();
        if ((textAreaDescr.getText().isEmpty())) {
            listException.add("Введите описание.");
        } else {
            this.textAreaDescrValid = textAreaDescr.getText();
        }
        if (radioButtonGroup.getSelectedToggle() == null) {
            listException.add("Укажите важность.");
        } else {
            this.importanceValid = Importance.valueOf(radioButtonGroup.getSelectedToggle().toString().split("'")[1]);
        }
        if (dateDueDate.getValue() == null) {
            listException.add("Укажите дату.");
        } else if (dateDueDate.getValue().isBefore(LocalDate.now())) {
            listException.add("Дата не может быть в прошлом!");
        } else {
            if (textMinutes.getText().isEmpty() || textHours.getText().isEmpty()) {
                listException.add("Укажите время.");
            } else {
                LocalDate localDate = dateDueDate.getValue();
                LocalDateTime localDateTime = localDate.atTime(Integer.parseInt(textHours.getText()), Integer.parseInt(textMinutes.getText()));
                if (localDateTime.compareTo(LocalDateTime.now()) == 0 || localDateTime.compareTo(LocalDateTime.now()) == -1) {
                    listException.add("время введено не верно");
                } else {
                    this.dueDateValid = localDateTime;
                }
            }
        }
        return listException;
    }

    /**
     * Method for calling the error window
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
     * Method for calling the successful execution window
     * @param textMessage
     */
    private void alertSuccessful(String textMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно");
        alert.setHeaderText(null);
        alert.setContentText(textMessage);
        alert.showAndWait();
        closeDialog();
    }

    /**
     * A method to close the change task window
     */
    private void closeDialog() {
        stage.close();
    }

    /**
     * A method that checks if the data have changed when the task is updated
     * @return
     */
    private boolean excludeOverwritingDuplicate(){
        if(rowData.getDescr()==textAreaDescrValid && rowData.getDate().equals(dueDateValid) && rowData.getImportance()==importanceValid){
            return true;
        } else
            return false;
    }
}

