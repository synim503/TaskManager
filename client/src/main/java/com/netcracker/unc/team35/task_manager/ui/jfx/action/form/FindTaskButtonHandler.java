package com.netcracker.unc.team35.task_manager.ui.jfx.action.form;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.model.TaskModel;
import com.netcracker.unc.team35.task_manager.ui.network.WebClientApi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class containing logic when searching for tasks
 * @author unc 21-22
 * @version 1.0
 */
public class FindTaskButtonHandler implements EventHandler<ActionEvent> {
    private TableView<com.netcracker.unc.team35.task_manager.ui.jfx.model.TaskModel> tableView;
    private ObservableList<com.netcracker.unc.team35.task_manager.ui.jfx.model.TaskModel> tasksData = FXCollections.observableArrayList();
    private ToggleGroup radioButtonGroup;
    private TextArea textAreaDescr;
    private DatePicker datePeriodFrom;
    private DatePicker datePeriodTo;

    private String description;
    private Importance importance;
    private LocalDateTime dueDate;
    private String searchTermsTimeValid;
    private LocalDateTime datePeriodFromValid;
    private LocalDateTime datePeriodToValid;

    /**
     * Instantiates a new Find task handler.
     *
     * @param tableView        the table view
     * @param radioButtonGroup the radio button group
     * @param textAreaDescr    the text area descr
     * @param datePeriodFrom   the date period from
     * @param datePeriodTo     the date period to
     */
    public FindTaskButtonHandler(TableView tableView, ToggleGroup radioButtonGroup,
                                 TextArea textAreaDescr,
                                 DatePicker datePeriodFrom,
                                 DatePicker datePeriodTo) {
        this.radioButtonGroup = radioButtonGroup;
        this.textAreaDescr = textAreaDescr;
        this.tableView = tableView;
        this.datePeriodFrom = datePeriodFrom;
        this.datePeriodTo = datePeriodTo;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        tasksData.clear();
        parseInput();

        Map<String, Object> map = buildParams();

        try {
            List<TaskModel> tasks = findTasksByParameters(map);
            for (TaskModel task : tasks) {
                tasksData.add(
                    new com.netcracker.unc.team35.task_manager.ui.jfx.model.TaskModel(
                        task.getId().toString(), task.getDescr(), task.getDueDate(), task.getImportance(), task.getStatus()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableView.setItems(tasksData);
        clearFormData();
    }

    /**
     * Find tasks by parameters list.
     * Rest API.
     *
     * @param params the params
     * @return the list
     */
    @SneakyThrows
    public List<TaskModel> findTasksByParameters(Map<String,Object> params) {
        Unirest.setTimeouts(0, 0);

        HttpResponse<String> response =
                Unirest.get(WebClientApi.REST_API_URL + "/api/v1/tasks/")
                        .queryString(params)
                        .asString();
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        List<TaskModel> tasks = objectMapper.readValue(response.getBody(), new TypeReference<List<TaskModel>>(){});
        return tasks;
    }

    /**
     * Time validation
     */

    private void parseInput() {
        if (!textAreaDescr.getText().isEmpty()) {
            this.description = textAreaDescr.getText();
        }
        if (this.radioButtonGroup.getSelectedToggle() != null) {
            this.importance = Importance.valueOf(radioButtonGroup.getSelectedToggle().toString().split("'")[1]);
        }
        if (this.datePeriodFrom.getValue() != null) {
            this.datePeriodFromValid = datePeriodFrom.getValue().atTime(0, 0);
        }
        if (this.datePeriodTo.getValue() != null) {
            this.datePeriodToValid = datePeriodTo.getValue().atTime(23, 59);
        }
    }

    /**
     *  The method collects parameters for a search request
     */
    private Map<String, Object> buildParams() {
        Map map = new HashMap<String, Object>();

        map.put("descr", this.description != null ? description : "");
        map.put("dueFrom", this.datePeriodFromValid != null ? this.datePeriodFromValid.toString() : "");
        map.put("dueTo", this.datePeriodToValid != null ? this.datePeriodToValid.toString() : "");
        map.put("importance", this.importance != null ? this.importance.toString() : "");

        return map;
    }

    /**
     * Method for clearing data from the application window
     */
    private void clearFormData() {
        this.description = null;
        this.importance = null;
        this.datePeriodFromValid = null;
        this.datePeriodToValid = null;
    }

}
