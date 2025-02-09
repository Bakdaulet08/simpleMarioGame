package com.example.mygame;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HelloController {
    private Image noHeart = new Image("C:\\Users\\noutshopkz\\IdeaProjects\\myGame\\src\\main\\resources\\images\\noHeart.png");
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonRestart;

    @FXML
    private Label labelPause, gameOver;

    @FXML
    private ImageView bg1, bg2,player, enemy, firstHeart, secondHeart, thirdHeart;

    private TranslateTransition enemyTransition;

    public static boolean right = false;
    public static boolean left = false;
    public static boolean jump = false;
    private static boolean isLose = false;

    public static boolean isPause = false;

    private static int counterHeart = 3;

    private int playerSpeed = 3, jumpDownSpeed = 5;
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (jump && player.getLayoutY() > 45f){
                player.setLayoutY(player.getLayoutY() - (playerSpeed+3));

            }
            else if (player.getLayoutY() <= 160f){
                jump = false;
                player.setLayoutY(player.getLayoutY() + playerSpeed-1.5);

            }
            if (right && player.getLayoutX() < 300f){
                player.setLayoutX(player.getLayoutX() + playerSpeed);
            }
            if (left && player.getLayoutX() > 5f){
                player.setLayoutX(player.getLayoutX() - playerSpeed);
            }

            if (isPause && !labelPause.isVisible() ){
                playerSpeed = 0;
                parallelTransition.pause();
                enemyTransition.pause();
                labelPause.setVisible(true);
            }else if (!isPause && labelPause.isVisible()){
                playerSpeed = 3;
                parallelTransition.play();
                enemyTransition.play();
                labelPause.setVisible(false);

            }
            if (player.getBoundsInParent().intersects(enemy.getBoundsInParent())){
                counterHeart -=1;
                if (counterHeart == 2){
                    thirdHeart.setImage(noHeart);
                }if (counterHeart == 1){
                    secondHeart.setImage(noHeart);
                }if (counterHeart == 0){
                    firstHeart.setImage(noHeart);
                    playerSpeed = 0;
                    parallelTransition.pause();
                    enemyTransition.pause();
                    gameOver.setVisible(true);
                    isLose = true;
                    buttonRestart.setVisible(true);

                }


            }
        }
    };
    public void restart(ActionEvent event) {
        // Сброс состояния игры
        counterHeart = 3;
        firstHeart.setImage(new Image("C:\\Users\\noutshopkz\\IdeaProjects\\myGame\\src\\main\\resources\\images\\heart.png"));
        secondHeart.setImage(new Image("C:\\Users\\noutshopkz\\IdeaProjects\\myGame\\src\\main\\resources\\images\\heart.png"));
        thirdHeart.setImage(new Image("C:\\Users\\noutshopkz\\IdeaProjects\\myGame\\src\\main\\resources\\images\\heart.png"));

        player.setLayoutX(44.0); // начальная позиция игрока
        player.setLayoutY(200.0); // начальная позиция игрока

        enemy.setLayoutX(789.0); // начальная позиция врага
        enemy.setLayoutY(268.0); // начальная позиция врага

        // Сброс состояния игры
        isLose = false;
        isPause = false;
        gameOver.setVisible(false);
        buttonRestart.setVisible(false);

        // Сброс переменных состояния
        jump = false;
        right = false;
        left = false;

        // Остановить и сбросить анимации
        if (parallelTransition != null) {
            parallelTransition.stop();
        }
        if (enemyTransition != null) {
            enemyTransition.stop();
        }

        // Перезапустить анимации
        initialize(); // Вызовите метод инициализации, чтобы запустить анимации заново

        timer.start(); // Запустите таймер
    }


    private final int BG_WIDTH = 700;

    private ParallelTransition parallelTransition;

    @FXML
    void initialize() {
        TranslateTransition bgOneTransition = new TranslateTransition(Duration.millis(5000), bg1);
        bgOneTransition.setFromX(0);
        bgOneTransition.setToX(BG_WIDTH * -1);
        bgOneTransition.setInterpolator(Interpolator.LINEAR);
        bgOneTransition.play();

        TranslateTransition bgTwoTransition = new TranslateTransition(Duration.millis(5000), bg2);
        bgTwoTransition.setFromX(0);
        bgTwoTransition.setToX(BG_WIDTH * -1);
        bgTwoTransition.setInterpolator(Interpolator.LINEAR);
        bgTwoTransition.play();

        enemyTransition = new TranslateTransition(Duration.millis(3500), enemy);
        enemyTransition.setFromX(0);
        enemyTransition.setToX(BG_WIDTH * -1 - 100);
        enemyTransition.setInterpolator(Interpolator.LINEAR);
        enemyTransition.setCycleCount(Animation.INDEFINITE);
        enemyTransition.play();

        parallelTransition = new ParallelTransition(bgOneTransition, bgTwoTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        timer.start();
    }

}
