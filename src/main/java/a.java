import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class a extends Application {
    Color fromColor;
    Color toColor;
    SimpleObjectProperty<Color> fromColorPro = new SimpleObjectProperty<>();
    SimpleObjectProperty<Color> toColorPro = new SimpleObjectProperty<>();
    Timeline timeline = new Timeline();
    Random r = new Random();
    Duration loopTime = Duration.millis(100);//渐变色变化周期
    //随机颜色
    private Color getRandomColor() {
        return Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }

    @Override
    public void start(Stage stage) throws Exception {
        // 颜色初始化
        fromColor = getRandomColor();
        toColor = getRandomColor();
        fromColorPro.set(fromColor);
        toColorPro.set(toColor);

        // 组件的设置
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);

        Rectangle rect = new Rectangle(280, 58);
        root.getChildren().addAll( rect);
        stage.setTitle("渐变色动画");
        stage.setScene(new Scene(root, 500, 300));

        toColorPro.addListener((ob, oc, nv) -> {
            rect.setFill(nv);
        });
        stage.show();
        addNewKeyFrames();
        timeline.setOnFinished(e -> {
            addNewKeyFrames();
            timeline.play();
        });

        timeline.play();

    }

    private void addNewKeyFrames() {
        timeline.getKeyFrames().clear();
        fromColor = getRandomColor();
        toColor = getRandomColor();
        KeyFrame kf2 = new KeyFrame(loopTime, new KeyValue(fromColorPro, fromColor),
                new KeyValue(toColorPro, toColor));
        timeline.getKeyFrames().addAll(kf2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
