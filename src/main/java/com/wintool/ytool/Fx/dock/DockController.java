package com.wintool.ytool.Fx.dock;
import com.wintool.ytool.Fx.StageManage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class DockController implements Initializable {
    private static Stage stage;
    public AnchorPane root;
    public void showUi() throws IOException {
        if (stage==null){
            stage= StageManage.getStageManage().getStage("-dockMain");
            Parent root = FXMLLoader.load(DockManage.class.getResource("dock_main.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(null);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
        }
        if (!stage.isShowing()){
            stage.show();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
