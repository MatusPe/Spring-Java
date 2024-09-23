package sk.tuke.gamestudio.game.Maze.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyhandler implements KeyListener {
    private int addx;
    private int addy;
    private String direction;
    private int stringCounter;
    public keyhandler(){
        this.direction= "/static/images/Owlet_Monster.png";

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();

        switch (code){
            case KeyEvent.VK_W :
                this.addy=-3;
                if(5>stringCounter) {
                    this.direction = "/static/images/Owlet_Monster_Climb_4.png";
                }else{
                    this.direction = "/static/images/back1.png";
                }
                break;
            case KeyEvent.VK_S:
                this.addy=3;
                if(5>stringCounter) {
                    this.direction = "/static/images/S.png";
                }else{
                    this.direction = "/static/images/1s.png";

                }
                break;
            case KeyEvent.VK_A:
                this.addx=-3;
                if(5>stringCounter) {
                    this.direction = "/static/images/A.png";
                }else{
                    this.direction = "/static/images/A1.png";
                }
                break;
            case KeyEvent.VK_D:
                this.addx=3;
                if(5>stringCounter) {
                    this.direction = "/static/images/formard.png";
                }else{
                    this.direction = "/static/images/forward.png";
                }
                break;
        }
        stringCounter++;
        if(stringCounter>10){
            stringCounter=0;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.addy=0;
        this.addx=0;

    }

    public int getAddx() {
        return addx;
    }

    public void setAddx(int addx) {
        this.addx = addx;
    }

    public int getAddy() {
        return addy;
    }

    public void setAddy(int addy) {
        this.addy = addy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
