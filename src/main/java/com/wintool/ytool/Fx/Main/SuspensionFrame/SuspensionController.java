package com.wintool.ytool.Fx.Main.SuspensionFrame;
import com.wintool.ytool.Fx.StageManage;
import javafx.animation.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class SuspensionController implements Initializable {
    public Rectangle rectangle;
    javafx.scene.paint.Color fromColor;
    javafx.scene.paint.Color toColor;
    SimpleObjectProperty<javafx.scene.paint.Color> fromColorPro = new SimpleObjectProperty<>();
    SimpleObjectProperty<Color> toColorPro = new SimpleObjectProperty<>();
    Timeline timeline = new Timeline();
    Random r = new Random();
    Duration loopTime = Duration.millis(1000);//渐变色变化周期
    public AnchorPane root;
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    //随机颜色
    private Color getRandomColor() {
        return Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }
    private void addNewKeyFrames() {
        timeline.getKeyFrames().clear();
        fromColor = getRandomColor();
        toColor = getRandomColor();
        KeyFrame kf2 = new KeyFrame(loopTime, new KeyValue(fromColorPro, fromColor),
                new KeyValue(toColorPro, toColor));
        timeline.getKeyFrames().addAll(kf2);
    }
    public void showUi() throws IOException {
        if (stage==null){
            stage= StageManage.getStageManage().getStage("悬浮框");
            stage.initStyle(StageStyle.TRANSPARENT);
        }
        stage.setHeight(100);
        stage.setWidth(100);
        if (stage.isShowing()){
            return;
        }
        if (!stage.isAlwaysOnTop()){
            stage.setAlwaysOnTop(true);
        }
        Parent root = FXMLLoader.load(getClass().getResource("suspension.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.setScene(scene);
        if (!stage.isShowing()){
            stage.show();
        }
    }
    public void CloseUi(){
        if (stage!=null&&stage.isShowing()){
            stage.close();
        }
    }
    private void isStageBecomeDeformed(MouseEvent mouseEvent){
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double X=mouseEvent.getScreenX() - xOffset;
        double Y=mouseEvent.getScreenY() - yOffset;
        if (X<100){
            stage.setWidth(20);
            stage.setX(0);
            rectangle.setWidth(20);
        }else if(bounds.getMaxX()-X<=100+stage.getWidth()){
            stage.setMaxWidth(20);
            stage.setWidth(20);
            rectangle.setWidth(20);
            stage.setX(bounds.getMaxX());
        }
        if (Y<100){
            stage.setY(0);
            stage.setHeight(20);
            rectangle.setHeight(20);
        } else if(bounds.getMaxY()-Y<=100+stage.getHeight()){
            stage.setMaxHeight(20);
            stage.setHeight(20);
            rectangle.setHeight(20);
            stage.setY(bounds.getMaxY());
        }
        if (!rectangle.isVisible()){
            rectangle.setVisible(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fromColor = getRandomColor();
        toColor = getRandomColor();
        fromColorPro.set(fromColor);
        toColorPro.set(toColor);
        toColorPro.addListener((ob, oc, nv) -> {
            rectangle.setFill(nv);
        });
        addNewKeyFrames();
        timeline.setOnFinished(e -> {
            addNewKeyFrames();
            timeline.play();
        });
        timeline.play();
        root.setOnMousePressed(event -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            event.consume();
            var stage = getStage();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        root.setOnMouseClicked(mouseEvent -> {
            var stage=getStage();
            try {
                if (mouseEvent.getClickCount()>1){
                    new StageListController().showUi(stage,stage.getX(),stage.getY());
                    if (stage.isShowing()){
                        stage.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        root.setOnMouseEntered(mouseEvent -> {
            getStage().setMaxWidth(100);
            getStage().setMaxHeight(100);
            getStage().setHeight(100);
            getStage().setWidth(100);
            rectangle.setHeight(100);
            rectangle.setWidth(100);
            if (rectangle.isVisible()){
                rectangle.setVisible(false);
            }
        });
        root.setOnMouseExited(this::isStageBecomeDeformed);
    }
    private Stage getStage() {
        if (stage == null) {
            stage = (Stage) root.getScene().getWindow();
        }
        return stage;
    }
}
