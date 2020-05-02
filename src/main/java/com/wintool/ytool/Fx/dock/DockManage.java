package com.wintool.ytool.Fx.dock;
import com.wintool.ytool.Fx.StageManage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.core.io.ClassPathResource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class DockManage implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    public AnchorPane root;
    private static Stage stage;
    public Button exit;
    public ImageView mainBg;
    public Slider size;
    public Slider h;
    public Slider rl;
    public ToggleGroup toggleGroup;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.setOnMouseDragged(event -> {
            event.consume();
            if (stage==null){
                stage = getStage();
            }
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        root.setOnMousePressed(event -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        exit.setOnAction(actionEvent -> {
            if (stage ==null){
                stage = getStage();
            }
            if (stage.isShowing()){
                stage.close();
            }
        });
        root.setOnMouseEntered(mouseEvent -> {
            try {
                ClassPathResource classPathResource = new ClassPathResource("com/wintool/ytool/Fx/Main/bg.png");
                File file = classPathResource.getFile();
                BufferedImage read = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(read, null);
                mainBg.setImage(image);
                mainBg.setPreserveRatio(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        size.valueProperty().addListener((observableValue, aBoolean, t1) -> {
            System.out.println(size.getValue());
        });
        rl.valueProperty().addListener((observableValue, aBoolean, t1) -> {
            System.out.println(rl.getValue());
        });
        h.valueProperty().addListener((observableValue, aBoolean, t1) -> {
            System.out.println(h.getValue());
        });
    }

    public static void showUi() throws IOException {
        if (stage==null){
            stage= StageManage.getStageManage().getStage("DockManage");
            Parent root = FXMLLoader.load(DockManage.class.getResource("dock_manage.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(null);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
        }
        if (!stage.isShowing()){
            stage.show();
        }
    }
    private Stage getStage() {
        if (stage == null) {
            stage = (Stage) root.getScene().getWindow();
        }
        return stage;
    }
    @FXML
    public void upDock(ActionEvent actionEvent) {

    }
    @FXML
    public void downDock(ActionEvent actionEvent) {

    }
    @FXML
    public void leftDock(ActionEvent actionEvent) {

    }
    @FXML
    public void rightDock(ActionEvent actionEvent) {

    }
}
