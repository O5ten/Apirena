package com.cybercom;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

import static javafx.application.Application.launch;

public class ApirenaApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Apirena Game App");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
