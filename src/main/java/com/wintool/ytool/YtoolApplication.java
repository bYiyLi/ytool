package com.wintool.ytool;

import com.wintool.ytool.Fx.Main.MainFx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static javafx.application.Application.launch;

@SpringBootApplication
public class YtoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(YtoolApplication.class, args);
        launch(MainFx.class,args);
    }
}
