package com.netcracker.unc.team35.task_manager.ui.jfx.controller;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.netcracker.unc.team35.task_manager.model.Importance;
import com.netcracker.unc.team35.task_manager.model.TaskModel;
import com.netcracker.unc.team35.task_manager.ui.jfx.model.PostponeModel;
import com.netcracker.unc.team35.task_manager.ui.network.WebClientApi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.SneakyThrows;

import java.net.URL;
import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The Notify controller.
 */
public class NotifyController {
    private List<TaskModel> tasks;
    private Stage stage;
    private List<HttpRequestWithBody> listRequest = new ArrayList<>();

    /**
     * Sets controller.
     *
     * @param tasks the tasks
     * @param stage the stage
     */
    public void setController(List<TaskModel> tasks, Stage stage) {
        this.tasks = tasks;
        this.stage = stage;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonOk;

    @FXML
    private Button buttonPostpone;

    @FXML
    private GridPane grdPane;

    /**
     * Initialize.
     */
    @SneakyThrows
    @FXML
    void initialize() {

        for (int i = 0; i < tasks.size(); i++) {
            TaskModel task = tasks.get(i);
            ComboBox<PostponeModel> comboBox = new ComboBox();
            comboBox.autosize();
            GridPane.setHgrow(comboBox, Priority.ALWAYS);
            PostponeModel[] postponeModelList;

            if(task.getImportance().equals(Importance.LOW)){
                postponeModelList = new PostponeModel[]{
                        new PostponeModel(Period.ofDays(1), "1 День"),
                        new PostponeModel(Period.ofDays(2), "2 Дня"),
                        new PostponeModel(Period.ofDays(3), "3 Дня")};
                for (int j = 0; j < 3; j++) {
                    comboBox.getItems().add(postponeModelList[j]);
                    comboBox.getSelectionModel().select(0);
                }
            }else if(task.getImportance().equals(Importance.HIGH)){
                postponeModelList = new PostponeModel[]{
                        new PostponeModel(Duration.ofHours(6), "6 Часов"),
                        new PostponeModel(Period.ofDays(12), "12 Часов"),
                        new PostponeModel(Period.ofDays(1), "1 День")};

                for (int j = 0; j < 3; j++) {
                    comboBox.getItems().add(postponeModelList[j]);
                    comboBox.getSelectionModel().select(0);
                }
            } else if(task.getImportance().equals(Importance.URGENT)){
                postponeModelList = new PostponeModel[]{
                        new PostponeModel(Duration.ofMinutes(30), "30 минут"),
                        new PostponeModel(Duration.ofHours(1), "1 час"),
                        new PostponeModel(Duration.ofHours(2), "2 часа")};

                for (int j = 0; j < 3; j++) {
                    comboBox.getItems().add(postponeModelList[j]);
                    comboBox.getSelectionModel().select(0);
                }
            }

            Button postponeButton = new Button("Отложить");
            Button canceledButton = new Button("Отменить");
            Button completeButton = new Button("Завершить");


            postponeButton.setOnAction(actionEvent -> {
                try {
                    listRequest.add(
                            Unirest.put(WebClientApi.REST_API_URL+"/api/v1/tasks/postpone?id={id}&period={period}")
                                    .queryString("period",comboBox.getSelectionModel().getSelectedItem().getPostponingPeriod().toString())
                                    .queryString("id",task.getId()));
                    canceledButton.setDisable(true);
                    completeButton.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            canceledButton.setOnAction(actionEvent -> {
                try {
                    listRequest.add(Unirest.put(WebClientApi.REST_API_URL+"/api/v1/tasks/cancel?id={id}").queryString("id", task.getId()));
                    postponeButton.setDisable(true);
                    completeButton.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            completeButton.setOnAction(actionEvent -> {
                try {
                    listRequest.add(Unirest.put(WebClientApi.REST_API_URL+"/api/v1/tasks/complete?id={id}").queryString("id", task.getId()));
                    postponeButton.setDisable(true);
                    canceledButton.setDisable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            grdPane.add(new Label(tasks.get(i).getDescr()), 0, i);
            grdPane.add(completeButton, 1, i);
            grdPane.add(canceledButton, 2, i);
            grdPane.add(postponeButton, 3, i);
            grdPane.add(comboBox,4,i);
        }

        buttonOk.setOnAction(actionEvent -> {
            for (HttpRequestWithBody request:listRequest
                 ) {
                try {
                    request.asString();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
            stage.fireEvent(new WindowEvent(
                    stage,
                    WindowEvent.WINDOW_CLOSE_REQUEST
            ));
        });
    }
}