package com.wintool.ytool.Fx.Main;

import com.wintool.ytool.Fx.StageManage;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class MainFx extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage stage= StageManage.getStageManage().getStage("Home");
        Scene scene = new Scene(root, 500, 309);
        scene.setFill(null);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        if (!stage.isShowing()){
            stage.show();
        }
    }
}
