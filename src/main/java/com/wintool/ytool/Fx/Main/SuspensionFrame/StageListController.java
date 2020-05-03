package com.wintool.ytool.Fx.Main.SuspensionFrame;

import com.wintool.ytool.Fx.StageManage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class StageListController implements Initializable {
    public static Stage stage;
    public AnchorPane root;
    private static Stage parent;
    private static double X;
    private static double Y;
    public VBox box;
    public static void showUi(Stage parent,double x, double y) throws IOException {
        X=x;
        Y=y;
        StageListController.parent=parent;
        if (stage==null){
            stage= StageManage.getStageManage().getStage("-窗口列表");
            stage.initStyle(StageStyle.TRANSPARENT);
            Parent root = FXMLLoader.load(StageListController.class.getResource("stage_list.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(null);
            stage.setScene(scene);
        }
        if (stage.isShowing()){
            return;
        }
        if (!stage.isAlwaysOnTop()){
            stage.setAlwaysOnTop(true);
        }
        stage.setX(x);
        stage.setY(y);
        if (!stage.isShowing()){
            stage.show();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.setOnMouseEntered(Event -> {
            Map<String, Stage> stageMap = StageManage.getStageManage().getStageMap();
            box.getChildren().clear();
            stageMap.forEach((index,item)->{
                ToggleButton button=new ToggleButton(index);
                button.getStylesheets().add("com/wintool/ytool/Fx/Main/SuspensionFrame/suspension.css");
                button.getStyleClass().add("stage_item");
                button.setSelected(item.isShowing());
                if (index.charAt(0)!='-'){
                    box.getChildren().add(button);
                }
                button.setOnMouseClicked(mouseEvent -> {
                    if (button.isSelected()){
                        if (!item.isShowing()){
                            item.show();
                        }
                    }else {
                        if (item.isShowing()){
                            item.close();
                        }
                    }
                });
            });
            stage.setHeight(40*(stageMap.size()-2)+20);
        });
    }

    public void closeUi(MouseEvent mouseEvent) {
        stage=getStage();
        if (stage.isShowing()){
            stage.close();
        }
        if (!parent.isShowing()){
            parent.setX(X);
            parent.setY(Y);
            parent.show();
        }
    }
    private Stage getStage() {
        if (stage == null) {
            stage = (Stage) root.getScene().getWindow();
        }
        return stage;
    }
}
