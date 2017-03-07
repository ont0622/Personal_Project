package Personal_Project.Penney_Game;

import java.text.DecimalFormat;

/**
 * This is a constructor for Penney_Game_Gui.
 * It will keep track of every data through out the game
 */
public class Penney_Game_Control {

    String sequence, console, p1Sec, p2Sec;
    int headCount, tailCount, p1Win, p2Win;

    Penney_Game_Control(){
        sequence = "-";
        console = "Waiting for flip";
        headCount = 0;
        tailCount = 0;
        p1Win = 0;
        p2Win = 0;
        p1Sec = "HTT";
        p2Sec = "TTT";
    }

    public String getSequence(){
        //if the sequence is too long, it will return substring of last 7 flips
        if (sequence.length() > 25){
            return "..." + sequence.substring(sequence.length()-20, sequence.length());
        }
        return sequence;
    }

    public void setSequence(char c){
        if (sequence.contains("-")){
            sequence = "" + c;
        }
        else if (c == 'R'){
            sequence = "-";
        }
        else
            this.sequence += c;
    }

    public void setP1Sec(String s){
        this.p1Sec = s.toUpperCase();
    }

    public String getP1Sec(){
        return p1Sec;
    }

    public void setP2Sec(String s){
        this.p2Sec = s.toUpperCase();
    }

    public String getP2Sec(){
        return p2Sec;
    }

    public int getP1Win(){
        return p1Win;
    }

    public void addP1Win(){
        this.p1Win++;
    }

    public int getP2Win(){
        return p2Win;
    }

    public void addP2Win(){
        this.p2Win++;
    }

    public int getHeadCount(){
        return headCount;
    }

    public int getTailCount(){
        return tailCount;
    }

    public float getHeadToTailRatio(){
        if (tailCount == 0){
            return 0;
        }
        return (float) headCount/tailCount;
    }

    public void setConsole(String input){
        this.console = input;
    }
    public String getConsole(){
        return console;
    }

    public int getheadCount(){
        return headCount;
    }

    public void addheadCount(){
        this.headCount++;
    }

    public int gettailCount(){
        return tailCount;
    }

    public void addtailCount(){
        this.tailCount++;
    }

    public float getP1Chance(){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (p1Win == 0 || p2Win == 0){
            return 0;
        }
        return (float) p1Win/(p1Win+p2Win)*100;
    }

    public float getP2Chance(){
        if (p1Win == 0 || p2Win == 0){
            return 0;
        }
        return (float) p2Win/(p1Win+p2Win)*100;
    }

    public int getTotal(){
        return headCount + tailCount;
    }
}
