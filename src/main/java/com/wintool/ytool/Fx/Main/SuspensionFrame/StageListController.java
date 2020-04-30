package com.wintool.ytool.Fx.Main.SuspensionFrame;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import com.wintool.ytool.Fx.StageManage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StageListController implements Initializable {
    public static Stage stage;
    public AnchorPane root;
    private static Stage parent;
    private static double X;
    private static double Y;
    public VBox box;
    public void showUi(Stage parent,double x, double y) throws IOException {
        X=x;
        Y=y;
        StageListController.parent=parent;
        if (stage==null){
            stage= StageManage.getStageManage().getStage("窗口列表");
            stage.initStyle(StageStyle.TRANSPARENT);
        }
        if (stage.isShowing()){
            return;
        }
        if (!stage.isAlwaysOnTop()){
            stage.setAlwaysOnTop(true);
        }
        Parent root = FXMLLoader.load(getClass().getResource("stage_list.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.setScene(scene);
        stage.setX(x);
        stage.setY(y);
        if (!stage.isShowing()){
            stage.show();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.setOnMouseExited(this::closeUi);
        StageManage.getStageManage().getStageMap().forEach((index,item)->{
            ToggleButton button=new ToggleButton(index);
            button.getStylesheets().add("com/wintool/ytool/Fx/Main/SuspensionFrame/suspension.css");
            button.getStyleClass().add("stage_item");
            button.setSelected(item.isShowing());
            if (!index.equalsIgnoreCase("窗口列表")&&!index.equalsIgnoreCase("悬浮框")){
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

    }

    private void closeUi(MouseEvent mouseEvent) {
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
