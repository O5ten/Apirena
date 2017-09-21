package com.cybercom;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.almasb.fxgl.core.math.FXGLMath.random;
import static com.almasb.fxgl.core.math.FXGLMath.randomBoolean;
import static java.lang.Integer.parseInt;

public class ApirenaApp extends GameApplication {

    public static final int TURN_TIMEOUT_IN_SECONDS = 30;
    private List<GameEntity> players = Lists.newArrayList();
    private Timer clock = new Timer();
    private Texture brickTexture;
    private Texture grassTexture;
    private GameEntity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Apirena Visualizer");
        settings.setVersion("1.0-SNAPSHOT");
        settings.setProfilingEnabled(false);
        settings.setIntroEnabled(false);
        settings.setMenuEnabled(false);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setCloseConfirmation(false);
    }

    @Override
    protected void initGame() {
        player = Entities.builder()
                .at(400, 400)
                .viewFromTexture("player_40.png")
                .build();
    }

    @Override
    protected void initUI() {
        this.brickTexture = getAssetLoader().loadTexture("bricks_40.png");
        this.grassTexture = getAssetLoader().loadTexture("grass_40.png");
        EntityView gameView = new EntityView();
        for(int i = 0; i < getWidth()/40; i++){
            for(int j = 0; j < getHeight()/40; j++){
                int chanceOfBrick = random(5);
                if(chanceOfBrick%5 == 0){
                    placeBlock(i, j, gameView, this.brickTexture.copy());
                }else{
                    placeBlock(i, j, gameView, this.grassTexture.copy());
                }
            }
        }
        getGameScene().addGameView(gameView);

        Text timerText = new Text();
        timerText.setStyle("-webkit-fx-background-color: black;-moz-fx-background-color: black;-ms-fx-background-color: black;-o-fx-background-color: black;-khtml-fx-background-color: black;fx-background-color: black;");
        timerText.setFill(Color.WHITESMOKE);
        timerText.setTranslateX(25);
        timerText.setTranslateY(25);
        timerText.textProperty().bind(
                new SimpleStringProperty("Next turn: ").concat(
                        getGameState().intProperty("turnclock").asString()
                )
        );

        getGameScene().addUINode(timerText); // add to the scene graph
        getGameWorld().addEntity(player);
    }

    private void placeBlock(int i, int j, EntityView view, Texture baseTexture){
        Texture newTexture = baseTexture.copy();
        newTexture.setTranslateX(i*40);
        newTexture.setTranslateY(j*40);
        view.addNode(newTexture);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("turnclock", 10);
        vars.put("turn", 0);
        clock.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int timeLeft = getGameState().getInt("turnclock");
                if(timeLeft <= 0){
                    executeTurn();
                    getGameState().setValue("turnclock", TURN_TIMEOUT_IN_SECONDS);
                }else{
                    getGameState().increment("turnclock", -1);
                }
            }
        }, 1000, 1000);
    }

    private void executeTurn(){
        getGameState().increment("turn", +1);
        int currentTurn = getGameState().getInt("turn");
        showAndHideTurnNumber(currentTurn);
        System.out.println("<======= Turn " + getGameState().getInt("turn") + " =======>");
    }

    private void showAndHideTurnNumber(int turn){
        Text textPixels = new Text("Turn " + String.valueOf(turn));
        textPixels.setFont(new Font("Helvetica", 40));
        textPixels.setFill(Color.WHITESMOKE);
        textPixels.setTranslateX(getWidth()/2-50);
        textPixels.setTranslateY(getHeight()/2);
        Platform.runLater(() -> getGameScene().addUINode(textPixels));
        clock.schedule(new TimerTask(){
            @Override
            public void run() {
                Platform.runLater(() -> getGameScene().removeUINode(textPixels));
            }
        }, 1000);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
