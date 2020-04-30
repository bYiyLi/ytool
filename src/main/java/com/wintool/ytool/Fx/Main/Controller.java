package com.wintool.ytool.Fx.Main;

import com.wintool.ytool.Fx.Main.SuspensionFrame.SuspensionController;
import com.wintool.ytool.Fx.dock.DockIco;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import org.springframework.core.io.ClassPathResource;

public class Controller implements Initializable {
    public Pane root;
    public Button exit;
    public Button set;
    public Pane setPane;
    public ImageView setPaneBg;
    public Pane main;
    public ImageView mainBg;
    public ImageView setMainBg;
    public Button setImageOk;
    public Button setImageNo;
    public Pane setMainBgPane;
    public CheckBox startUpCheckBox;
    public GridPane appGrid;
    public AnchorPane appPane;
    public ImageView appPaneBg;
    public CheckBox suspensionCheckBox;
    private boolean setPaneBool = false;
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private SuspensionController suspensionController;
    private void readConfig(){
        ClassPathResource classPathResource = new ClassPathResource("com/wintool/ytool/Fx/Main/SuspensionFrame/config.txt");
        try {
            FileReader config=new FileReader(classPathResource.getFile());
            char[] c=new char[4];
            if (config.read(c)>0&&"true".equalsIgnoreCase(String.valueOf(c))){
                suspensionCheckBox.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void suspensionCheckBoxOnAction(ActionEvent actionEvent){
        if (suspensionCheckBox.isSelected()){
            if (suspensionController==null){
                suspensionController=new SuspensionController();
            }
            try {
                suspensionController.showUi();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if (suspensionController!=null){
                suspensionController.CloseUi();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readConfig();
        suspensionCheckBoxOnAction(new ActionEvent());
        startUpCheckBox.setOnAction(actionEvent -> {
            if (startUpCheckBox.isSelected()) {
                System.out.println("开机启动");
            } else {
                System.out.println("无");
            }
        });
        suspensionCheckBox.setOnAction(this::suspensionCheckBoxOnAction);
        setMainBgPane.setOnDragEntered((event) -> {
            setMainBgPane.setStyle("-fx-border-color: #6a4acd;");
        });
        setMainBgPane.setOnDragExited((event) -> {
            setMainBgPane.setStyle("-fx-border-color: azure;");
        });
        setMainBgPane.setOnDragOver(event -> {
            event.acceptTransferModes(event.getTransferMode());
        });
        setMainBgPane.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            List<File> files = dragboard.getFiles();
            if (files.size() == 1) {
                File file = files.get(0);
                try {
                    setMainBg.setImage(new Image(new FileInputStream(file)));
                    setMainBg.setVisible(true);
                    setMainBg.setPreserveRatio(false);
                    setImageNo.setVisible(true);
                    setImageOk.setVisible(true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        exit.setOnAction(actionEvent -> {
            var stage = getStage();
            stage.close();
        });
        root.setOnMouseDragged(event -> {
            event.consume();
            var stage = getStage();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        root.setOnMousePressed(event -> {
            event.consume();
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        setPaneBg.setEffect(new GaussianBlur(8));
        setPaneBg.prefWidth(500);
        set.setOnAction(actionEvent -> {
            if (!setPane.isVisible()) {
                setPane.setVisible(!setPane.isVisible());
                setPane.setTranslateY(309);
            }
            TranslateTransition transition = new TranslateTransition(Duration.millis(1300), setPane);
            if (setPaneBool) {
                transition.setFromY(setPane.getTranslateY());
                transition.setToY(309);
            } else {
                transition.setFromY(setPane.getTranslateY());
                transition.setToY(0);
            }
            transition.play();
            this.setPaneBool = !this.setPaneBool;
        });
        setPane.translateYProperty().addListener((observable, oldV, newV) -> {
            int h = 309 - newV.intValue();
            if (h > 1) {
                WritableImage image = new WritableImage(500, h);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setViewport(new Rectangle2D(0, newV.intValue(), 500, 309));
                main.snapshot(snapshotParameters, image);
                setPaneBg.setImage(image);
            }
        });
        mainBg.setPreserveRatio(false);
        appPane.setOnMouseExited(mouseDragEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), appPane);
            transition.setFromX(appPane.getTranslateX());
            transition.setToX(0);
            transition.play();
        });
        appPane.setOnMouseEntered(mouseEvent -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), appPane);
            transition.setFromX(appPane.getTranslateX());
            transition.setToX(400);
            transition.play();
        });
        appPane.translateXProperty().addListener((observable, oldV, newV) -> {
            int w = 20+newV.intValue();
            WritableImage image = new WritableImage(400, 270);
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setViewport(new Rectangle2D(w-400,25, w, 295));
            main.snapshot(snapshotParameters, image);
            appPaneBg.setImage(image);
        });
        WritableImage image = new WritableImage(400, 270);
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setViewport(new Rectangle2D(-380,25, 20, 295));
        main.snapshot(snapshotParameters, image);
        appPaneBg.setImage(image);
        appPaneBg.setEffect(new GaussianBlur(8));
        transition();
        initApp();
    }
    private void transition(){
        // 创建一个旋转的过渡动画，构建方法中，第一个参数指定完成一次旋转所需要的时间，第二个参数是旋转的对象
        RotateTransition rt = new RotateTransition(Duration.millis(2000), set);
        rt.setByAngle(360);  // 设置旋转的角度
        rt.setCycleCount(Animation.INDEFINITE);  // 设置旋转次数，我们需要旋转无数次
        rt.setInterpolator(Interpolator.LINEAR);  // 控制每个过渡周期的加速和减速时间，设置为匀速
        rt.play();  // 开始播放动画
    }
    private Stage getStage() {
        if (stage == null) {
            stage = (Stage) root.getScene().getWindow();
        }
        return stage;
    }
    public void setBgOkAction(ActionEvent actionEvent) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("com/wintool/ytool/Fx/Main/bg.png");
            File file = classPathResource.getFile();
            Image image = setMainBg.getImage();
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            boolean png = ImageIO.write(bImage, "png", file);
            if (png) {
                mainBg.setImage(image);
                mainBg.setPreserveRatio(false);
                setPaneBg.setImage(image);
                setBgNoAction(actionEvent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setBgNoAction(ActionEvent actionEvent) {
        setMainBg.setVisible(false);
        setImageOk.setVisible(false);
        setImageNo.setVisible(false);
    }
    public void initApp(){
        try {
            appGrid.add(DockIco.getApp(),1,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
