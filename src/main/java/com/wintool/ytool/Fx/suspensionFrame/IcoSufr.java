package com.wintool.ytool.Fx.suspensionFrame;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public  class IcoSufr implements Initializable {
    public ImageView ico;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ico.setPreserveRatio(false);
        RotateTransition rt = new RotateTransition(Duration.millis(2000), ico);
        rt.setByAngle(360);  // 设置旋转的角度
        rt.setCycleCount(Animation.INDEFINITE);  // 设置旋转次数，我们需要旋转无数次
        rt.setInterpolator(Interpolator.LINEAR);  // 控制每个过渡周期的加速和减速时间，设置为匀速
        rt.play();  // 开始播放动画
    }
    public  void StartApp(ActionEvent actionEvent){
        System.out.println("a");
    };
    public static Parent getApp() throws IOException {
        return FXMLLoader.load(IcoSufr.class.getResource("ico_sufr.fxml"));
    }
}
