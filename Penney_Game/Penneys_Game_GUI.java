package Personal_Project.Penney_Game;

/**
 * Created by Daniel Oh on 2/27/2017.
 * This program is GUI version of simulation for Penney's game
 *  https://en.wikipedia.org/wiki/Penney's_game
 * This program will accept any sequence from user and use the sequences to run simulations.
 */

import javafx.animation.*;
import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import javafx.concurrent.*;
import javafx.util.Duration;

public class Penneys_Game_GUI extends Application  {

    //calling controller to keep track of data
    static Penney_Game_Control PGC = new Penney_Game_Control();
    static Label currentSequence, console, p1WinN, p2WinN, headCountN,
            tailCountN, headToTailN, p1WinChance, p2WinChance, totalFlip;
    static Timeline timeline = new Timeline();
    static double computeSpeed;
    static Slider speed;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException{

        final int MAX_WIDTH = 800;
        final int MAX_HEIGHT = 500;

        //setting up grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10,10,20,20));

        //Row 1,2
        primaryStage.setTitle("Penney's Game");
        Text sceneTitle = new Text("Penney's Game");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Text danielOh = new Text("Daniel Oh | http://github.com/ont0622");
        danielOh.setFont(Font.font("Tahoma", FontWeight.LIGHT, 12));
        danielOh.setFill(Color.LIGHTGREY);
        Label player1 = new Label("Player 1");
        player1.setFont(Font.font("Constantia", FontWeight.NORMAL, 13));
        Label player2 = new Label("Player 2");
        player2.setFont(Font.font("Constantia", FontWeight.NORMAL, 13));
        TextField player1Field = new TextField(PGC.getP1Sec());
        player1Field.setAlignment(Pos.CENTER);
        TextField player2Field = new TextField(PGC.getP2Sec());
        player2Field.setAlignment(Pos.CENTER);
        HBox inputBox = new HBox();
        inputBox.setSpacing(25);
        inputBox.setPadding(new Insets(10,10,10,10));

        grid.add(sceneTitle,0,0,1,1);
        grid.add(danielOh, 3, 0, 2, 1);

        inputBox.getChildren().addAll(player1,player1Field,player2,player2Field);
        grid.add(inputBox,0,1,4,1);

        //Row 3
        currentSequence = new Label(PGC.getSequence());
        currentSequence.setFont(Font.font("Consolas", FontWeight.BOLD, 15));
        grid.add(currentSequence,1,2,4,1);

        //Row 4
        Button flip_1 = new Button("1 Flip");
        Button flip_10 = new Button("10 Flip");
        Button flip_100 = new Button("100 Flip");
        Button flip_1000 = new Button("1000 Flip");
        Button flip_manual = new Button("Flips");
        Button flip_infinite = new Button("Infinite Flips");
        Button pauseB = new Button("Pause");
        Button reset = new Button("Reset");
        reset.setFont(Font.font("Constantia", FontWeight.BOLD, 12));
        flip_1.setMaxWidth(Double.MAX_VALUE);
        flip_10.setMaxWidth(Double.MAX_VALUE);
        flip_100.setMaxWidth(Double.MAX_VALUE);
        flip_1000.setMaxWidth(Double.MAX_VALUE);
        flip_manual.setMaxWidth(Double.MAX_VALUE);
        flip_infinite.setMaxWidth(Double.MAX_VALUE);
        pauseB.setMaxWidth(Double.MAX_VALUE);
        reset.setMaxWidth(Double.MAX_VALUE);
        TextField input_manual_flip = new TextField("0");
        input_manual_flip.setPrefWidth(150);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5,10,5,10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(flip_1,flip_10,flip_100,flip_1000, input_manual_flip, flip_manual, reset);
        grid.add(hbox, 0, 3, 4, 1);

        //Row 5
        Label headCount = new Label("Head:");
        headCount.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        headCountN = new Label(String.valueOf(PGC.getHeadCount()));
        headCountN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        Label tailCount = new Label("Tail:");
        tailCount.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        tailCountN = new Label(String.valueOf(PGC.getTailCount()));
        tailCountN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        HBox headTailBox = new HBox();
        headTailBox.setPadding(new Insets(5,10,5,10));
        headTailBox.setSpacing(20);
        headTailBox.getChildren().addAll(headCount,headCountN,tailCount,tailCountN);
        grid.add(headTailBox,0,5,4,1);

        //Row 6
        Label headToTail = new Label("Head to Tail ratio:");
        headToTail.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        headToTailN = new Label(String.format("%.2f",PGC.getHeadToTailRatio()));
        headToTailN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        HBox headTailNBox = new HBox();
        headTailNBox.setPadding(new Insets(5,10,5,10));
        headTailNBox.setSpacing(20);
        headTailNBox.getChildren().addAll(headToTail,headToTailN);
        grid.add(headTailNBox, 0,6,4,1);

        //Row 7
        Label p1Win = new Label("Player 1 Wins:");
        p1Win.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        p1WinN = new Label(String.valueOf(PGC.getP1Win()));
        p1WinN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        Label p2Win = new Label("Player 2 Wins:");
        p2Win.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        p2WinN = new Label(String.valueOf(PGC.getP2Win()));
        p2WinN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        HBox p2box = new HBox();
        p2box.setPadding(new Insets(10,10,10,10));
        p2box.setSpacing(25);
        p2box.getChildren().addAll(p1Win,p1WinN, p2Win,p2WinN);
        grid.add(p2box,0,7,4,1);

        //Row 8
        p1WinChance = new Label("Player 1 Win Rate: " + String.format("%2.2f",PGC.getP1Chance()) + "%");
        p2WinChance = new Label("| Player 2 Win Rate: " + String.format("%2.2f",PGC.getP2Chance()) + "%");
        p1WinChance.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        p2WinChance.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));

        HBox rateBox = new HBox();
        rateBox.setPadding(new Insets(5,10,5,10));
        rateBox.setSpacing(20);
        rateBox.getChildren().addAll(p1WinChance,p2WinChance);
        grid.add(rateBox, 0,9,4,1);

        //Row 9
        Label total = new Label("Total Flips: ");
        total.setFont(Font.font("Constantia", FontWeight.NORMAL,12));
        totalFlip = new Label("" + (PGC.getHeadCount()+PGC.getTailCount()));
        totalFlip.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        HBox totalBox = new HBox();
        totalBox.setPadding(new Insets(5,10,5,10));
        totalBox.setSpacing(20);
        totalBox.getChildren().addAll(total,totalFlip);
        grid.add(totalBox,0,10,4,1);

        //Row 11
        HBox infiniteBox = new HBox();
        infiniteBox.setPadding(new Insets(5,10,5,10));
        infiniteBox.setSpacing(20);
        infiniteBox.getChildren().addAll(flip_infinite,pauseB);
        grid.add(infiniteBox,0,11,4,1);

        //Row 12
        Label speedL = new Label("Infinite Run Speed: ");
        Label slowL = new Label("Slow");
        Label fastL = new Label("Fast");
        computeSpeed = 100;
        speed = new Slider(0,10,computeSpeed/100);
        speed.setShowTickLabels(true);
        speed.setShowTickMarks(true);
        speed.setMajorTickUnit(1);
        speed.setBlockIncrement(5);
        final Label speedLabel = new Label(String.valueOf(speed.getValue()) + " Times");
        HBox sliderBox = new HBox();
        sliderBox.setPadding(new Insets(5,10,5,10));
        sliderBox.setSpacing(20);
        sliderBox.getChildren().addAll(speedL, slowL, speed, fastL,speedLabel);
        grid.add(sliderBox,0,12,4,1);

        //Row 13
        console = new Label(PGC.getConsole());
        console.setFont(Font.font("Constantia",FontWeight.BOLD, 15));
        grid.add(console,3,13,4,1);

        //EventHandler
        flip_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                    coinFlip();
                PGC.setConsole("1 Coin Flipped");
            }
        });

        flip_10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                realTimeFlip(10);
            }
        });

        flip_100.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                realTimeFlip(100);
            }
        });

        flip_1000.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                realTimeFlip(1000);
            }
        });

        flip_manual.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                realTimeFlip(Integer.valueOf(input_manual_flip.getText()));
            }
        });

        flip_infinite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                realTimeFlip(Animation.INDEFINITE);
            }
        });

        pauseB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.stop();
                console.setText("Paused");
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC = new Penney_Game_Control();
                currentSequence.setText(PGC.getSequence());
                console.setText("Reset");
                p1WinN.setText(String.valueOf(PGC.p1Win));
                p2WinN.setText(String.valueOf(PGC.p2Win));
                headCountN.setText(String.valueOf(PGC.getHeadCount()));
                tailCountN.setText(String.valueOf(PGC.getTailCount()));
                headToTailN.setText(String.format("%.2f", PGC.getHeadToTailRatio()));
                p1WinChance.setText("Player 1 Win Rate: " + String.format("%.2f", PGC.getP1Chance()) + "%");
                p2WinChance.setText("| Player 2 Win Rate: " + String.format("%.2f", PGC.getP2Chance()) + "%");
                totalFlip.setText(String.valueOf(PGC.getTotal()));
                speed.setValue(1);
            }
        });

        speed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                computeSpeed = (newValue.doubleValue()*100);
                speedLabel.setText(String.format("%.2f Times", newValue));
                timeline.setRate(newValue.doubleValue());
            }
        });

        primaryStage.setMaxWidth(MAX_WIDTH);
        primaryStage.setScene(new Scene(grid, MAX_WIDTH, MAX_HEIGHT));
        primaryStage.show();

    }

    //It will use random seed to generate head or tail
    public static void coinFlip() {
        int randomNum = 1 + (int) (Math.random() * 2);
        if (randomNum == 1) {
            PGC.addheadCount();
            PGC.setSequence('H');
        } else {
            PGC.addtailCount();
            PGC.setSequence('T');
        }

        if (PGC.getSequence().contains(PGC.p1Sec)) {
            PGC.addP1Win();
            PGC.setSequence('R');
        }
        if (PGC.getSequence().contains(PGC.p2Sec)) {
            PGC.addP2Win();
            PGC.setSequence('R');
        }
        currentSequence.setText(PGC.getSequence());
        console.setText(PGC.getConsole());
        p1WinN.setText(String.valueOf(PGC.p1Win));
        p2WinN.setText(String.valueOf(PGC.p2Win));
        headCountN.setText(String.valueOf(PGC.getHeadCount()));
        tailCountN.setText(String.valueOf(PGC.getTailCount()));
        headToTailN.setText(String.format("%.2f", PGC.getHeadToTailRatio()));
        p1WinChance.setText("Player 1 Win Rate: " + String.format("%.2f", PGC.getP1Chance()) + "%");
        p2WinChance.setText("| Player 2 Win Rate: " + String.format("%.2f", PGC.getP2Chance()) + "%");
        totalFlip.setText(String.valueOf(PGC.getTotal()));
    }

    public void realTimeFlip(int counts){
        if (timeline.getStatus().equals(Animation.Status.RUNNING)){
            PGC.setConsole("Infinite Flip Already Running");
        }
        else {
            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO),
                    new KeyFrame(Duration.millis(computeSpeed/speed.getValue()), e -> {
                        coinFlip();
                    })
        );
        timeline.setCycleCount(counts);
        PGC.setConsole("Infinite Flip Running");
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                console.setText(String.format("%d Flips Completed", counts));
            }
        });
        }
    }
 }
