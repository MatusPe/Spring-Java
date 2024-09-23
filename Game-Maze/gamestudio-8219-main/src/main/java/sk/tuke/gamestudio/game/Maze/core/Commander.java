package sk.tuke.gamestudio.game.Maze.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Commander {

    Gamepanel gamepanel;
    keyhandler keyhandler;
    private int x;
    private int y;

    private mazeMap mazeMap;
    private char maze[][];
    private boolean check_terminal=false;
    private int Tile;


    public Commander(Gamepanel gamepanel, keyhandler keyhandler, mazeMap mazeMap){
        this.gamepanel=gamepanel;
        this.keyhandler=keyhandler;
        this.maze= mazeMap.getMaze();
        this.mazeMap=mazeMap;
        this.Tile= gamepanel.size;
        setdeafultposition();
        mazeMap.printMaze(this);

    }


    public void setdeafultposition(){
        this.x=60;
        this.y=52;
    }
    public void update(){

        this.check_terminal=gamepanel.getUserInteraction().DirectionFromTerminal(this);

        this.x+=keyhandler.getAddx();
        this.y+=keyhandler.getAddy();
        if(this.maze[this.x/48][y/48]=='#'||this.maze[(this.x+31)/48][y/48]=='#'||this.maze[(this.x)/48][(y+31)/48]=='#'){
            this.setX(this.getX()-this.keyhandler.getAddx());
            this.setY(this.getY()-this.keyhandler.getAddy());

        }else if((this.x / 48) ==gamepanel.getNumber()-2&&((this.y / 48) == gamepanel.getNumber()-2)){
            gamepanel.setThread(null);
            gamepanel.game=game.GAME_WON;

        }
        keyhandler.setAddx(0);
        keyhandler.setAddy(0);
        if(this.check_terminal) {
            this.mazeMap.printMaze(this);
            this.check_terminal=false;
        }


    }
    public void TerminalCordination(String string){

        switch (string.toLowerCase()){
            case "up":
                keyhandler.setAddy(-48);
                break;
            case "down":
                keyhandler.setAddy(48);
                break;
            case "right":
                keyhandler.setAddx(48);
                break;
            case "left":
                keyhandler.setAddx(-48);
                break;
        }


        //System.out.println(x);
    }
    public void draw(Graphics2D g2){

        BufferedImage image = null;
        try {
            image= ImageIO.read(Objects.requireNonNull(getClass().getResource(keyhandler.getDirection())));
        }catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(image, this.x, this.y, Tile - 16, Tile - 16, null);


    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
