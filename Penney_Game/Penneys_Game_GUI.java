package Personal_Project.Penney_Game;

/**
 * Created by Daniel Oh on 2/27/2017.
 * This program is GUI version of simulation for Penney's game
 *  https://en.wikipedia.org/wiki/Penney's_game
 * This program will accept any sequence from user and use the sequences to run simulations.
 */

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Penneys_Game_GUI extends Application  {

    //calling controller to keep track of data
    static Penney_Game_Control PGC = new Penney_Game_Control();
    static Label currentSequence, console, p1WinN, p2WinN, headCountN,
            tailCountN, headToTailN, p1WinChance, p2WinChance, totalFlip;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //setting up grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

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
        TextField player2Field = new TextField(PGC.getP2Sec());

        grid.add(sceneTitle,0,0,4,1);
        grid.add(danielOh, 3, 0, 2, 1);
        grid.add(player1, 0,1,1,1);
        grid.add(player1Field, 1, 1, 1, 1);
        grid.add(player2, 2,1,1,1);
        grid.add(player2Field, 3, 1, 1, 1);

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
        flip_1.setMaxWidth(Double.MAX_VALUE);
        flip_10.setMaxWidth(Double.MAX_VALUE);
        flip_100.setMaxWidth(Double.MAX_VALUE);
        flip_1000.setMaxWidth(Double.MAX_VALUE);
        flip_manual.setMaxWidth(Double.MAX_VALUE);
        TextField input_manual_flip = new TextField("0");
        input_manual_flip.setPrefWidth(150);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5,10,5,10));
        hbox.setSpacing(10);
        hbox.getChildren().addAll(flip_1,flip_10,flip_100,flip_1000, input_manual_flip, flip_manual);
        grid.add(hbox, 0, 3, 4, 1);

        //Row 5
        Label headCount = new Label("Head:");
        headCount.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        headCountN = new Label(String.format("%10d",PGC.getHeadCount()));
        headCountN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        Label tailCount = new Label("Tail:");
        tailCount.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        tailCountN = new Label(String.format("%10d",PGC.getTailCount()));
        tailCountN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        grid.add(headCount, 0, 4, 1, 1);
        grid.add(headCountN, 1, 4, 1, 1);
        grid.add(tailCount, 2, 4, 1, 1);
        grid.add(tailCountN, 3, 4, 1, 1);

        //Row 6
        Label headToTail = new Label("Head to Tail ratio:");
        headToTail.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        headToTailN = new Label(String.format("%.2f",PGC.getHeadToTailRatio()));
        headToTailN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        grid.add(headToTail, 0, 5, 1,1);
        grid.add(headToTailN, 1,5,1,1);

        //Row 7
        Label p1Win = new Label("Player 1 Wins:");
        p1Win.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        p1WinN = new Label(String.format("%10d",PGC.getP1Win()));
        p1WinN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        Label p2Win = new Label("Player 2 Wins:");
        p2Win.setFont(Font.font("Constantia", FontWeight.NORMAL, 12));
        p2WinN = new Label(String.format("%10d",PGC.getP2Win()));
        p2WinN.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        grid.add(p1Win, 0, 6, 1, 1);
        grid.add(p1WinN, 1, 6, 1, 1);
        grid.add(p2Win, 2, 6, 1, 1);
        grid.add(p2WinN, 3, 6, 1, 1);

        //Row 8
        p1WinChance = new Label("Player 1 Win Rate: " + String.format("%2.2f",PGC.getP1Chance()) + "%");
        p2WinChance = new Label("| Player 2 Win Rate: " + String.format("%2.2f",PGC.getP2Chance()) + "%");
        p1WinChance.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        p2WinChance.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        Button reset = new Button("Reset");
        reset.setFont(Font.font("Constantia", FontWeight.BOLD, 12));

        grid.add(p1WinChance, 1, 7, 1, 1);
        grid.add(p2WinChance, 2, 7, 1, 1);
        grid.add(reset, 4, 7, 2, 1);

        //Row 9
        Label total = new Label("Total Flips: ");
        total.setFont(Font.font("Constantia", FontWeight.NORMAL,12));
        totalFlip = new Label("" + (PGC.getHeadCount()+PGC.getTailCount()));
        totalFlip.setFont(Font.font("Consolas", FontWeight.BOLD, 12));
        grid.add(total, 1, 8, 1, 1);
        grid.add(totalFlip, 2, 8, 3, 1);


        //Row 10
        console = new Label(PGC.getConsole());
        console.setFont(Font.font("Calibri",FontWeight.BOLD, 15));
        grid.add(console, 2, 9, 3, 1);

        //EventHandler
        flip_1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                coinFlip();
                PGC.setConsole("1 Coin Flipped");
                refreshGUI();
            }
        });

        flip_10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                for (int i = 0; i < 10; i++){
                    coinFlip();
                }
                PGC.setConsole("10 Coins Flipped");
                currentSequence.setText(PGC.getSequence());
                refreshGUI();

            }
        });

        flip_100.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                for (int i = 0; i < 100; i++){
                    coinFlip();
                }
                PGC.setConsole("100 Coins Flipped");
                refreshGUI();
            }
        });

        flip_1000.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                for (int i = 0; i < 1000; i++){
                    coinFlip();
                }
                PGC.setConsole("1000 Coins Flipped");
                refreshGUI();
            }
        });

        flip_manual.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC.setP1Sec(player1Field.getText());
                PGC.setP2Sec(player2Field.getText());
                for (int i = 0; i < Integer.valueOf(input_manual_flip.getText()); i++){
                    coinFlip();
                }
                PGC.setConsole(input_manual_flip.getText() + " Coins Flipped");
                refreshGUI();
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PGC = new Penney_Game_Control();
                refreshGUI();
            }

        });

        primaryStage.setScene(new Scene(grid, 850, 350));
        primaryStage.show();

    }

    //It will use random seed to generate head or tail
    public static void coinFlip(){
        int randomNum = 1 + (int)(Math.random() * 2);
        if (randomNum == 1) {
            PGC.addheadCount();
            PGC.setSequence('H');
        } else{
            PGC.addtailCount();
            PGC.setSequence('T');
        }

        if (PGC.getSequence().contains(PGC.p1Sec)){
            PGC.addP1Win();
            PGC.setSequence('R');
        }
        if (PGC.getSequence().contains(PGC.p2Sec)){
            PGC.addP2Win();
            PGC.setSequence('R');
        }
    }

    //Everytime coin is flipped, it will refresh current GUI
    public static void refreshGUI(){
        currentSequence.setText(PGC.getSequence());
        console.setText(PGC.getConsole());
        p1WinN.setText(String.format("%10d",PGC.getP1Win()));
        p2WinN.setText(String.format("%10d",PGC.getP2Win()));
        headCountN.setText(String.format("%10d",PGC.getHeadCount()));
        tailCountN.setText(String.format("%10d",PGC.getTailCount()));
        headToTailN.setText(String.format("%.2f",PGC.getHeadToTailRatio()));
        p1WinChance.setText("Player 1 Win Rate: " + String.format("%.2f", PGC.getP1Chance()) + "%");
        p2WinChance.setText("| Player 2 Win Rate: " + String.format("%.2f", PGC.getP2Chance()) + "%");
        totalFlip.setText(String.valueOf(PGC.getTotal()));
    }
}
