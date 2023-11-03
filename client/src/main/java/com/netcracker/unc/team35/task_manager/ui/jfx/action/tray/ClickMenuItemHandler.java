package com.netcracker.unc.team35.task_manager.ui.jfx.action.tray;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
/*
* Абастрактный клас описывающий окна приложения
* */
public abstract class ClickMenuItemHandler implements EventHandler<ActionEvent> {
    private Stage primaryStage;

    @Override
    public void handle(ActionEvent actionEvent) {
        primaryStage = new Stage();
        primaryStage.setTitle("Task Manager");
        primaryStage.setWidth(getWidth());
        primaryStage.setHeight(getHeight());
        primaryStage.setResizable(false);

        String iconImageLoc = "/icon/icon.png";
        boolean backgroundLoading = false;
        Image image = new Image(iconImageLoc, backgroundLoading);
        primaryStage.getIcons().add(image);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(getPathFxml()));
        setController(loader);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public abstract void setController(FXMLLoader loader);
    abstract int getWidth();
    abstract int getHeight();
    abstract String getPathFxml();
    Stage getStage(){
        return primaryStage;
    }

}
