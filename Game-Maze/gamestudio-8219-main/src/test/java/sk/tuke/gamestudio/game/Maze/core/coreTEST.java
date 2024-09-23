package sk.tuke.gamestudio.game.Maze.core;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class coreTEST {
    @Test
    void isTherefinish(){
        mazeMap mazeMap=new mazeMap(7);
        mazeMap mazeMap1=new mazeMap(9);
        mazeMap mazeMap2=new mazeMap(12 );


        List<char[][]> listOfMazes = new ArrayList<>();
        listOfMazes.add(mazeMap.getMaze());
        listOfMazes.add(mazeMap1.getMaze());
        listOfMazes.add(mazeMap2.getMaze());
        boolean finish=false;
        for(char [][] Maze:listOfMazes){
            for(int i=0; i< Maze.length;i++){
                for(int j=0;j< Maze[i].length;j++){
                    if(Maze[i][j]=='$'){
                        finish=true;
                    }
                }
            }
            assertEquals(true, finish);
        }
    }
    @Test
    void checkGenerateMaze(){
        mazeMap mazeMap=new mazeMap(7);
        mazeMap mazeMap1=new mazeMap(9);
        mazeMap mazeMap2=new mazeMap(12);


        List<char[][]> listOfMazes = new ArrayList<>();
        listOfMazes.add(mazeMap.getMaze());
        listOfMazes.add(mazeMap1.getMaze());
        listOfMazes.add(mazeMap2.getMaze());
        boolean finish=true;
        for(char [][] Maze:listOfMazes){
            for(int i=1; i< Maze.length-1;i++){
                for(int j=1;j< Maze[i].length-1;j++){
                    if(Maze[i][j]==' '||Maze[i][j]=='$'){
                        if(Maze[i+1][j]==' '||Maze[i-1][j]==' '||Maze[i][j+1]==' '||Maze[i][j-1]==' '){

                        }else{
                            finish =false;
                        }
                    }
                }
            }
            assertEquals(true, finish);
        }
    }
    @Test
    void keyhandlerCheck(){
        keyhandler keyhandler=new keyhandler();
        JFrame frame = new JFrame();
        KeyEvent VK_W = new KeyEvent( frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(VK_W);
        assertEquals(-3, keyhandler.getAddy());

        KeyEvent VK_D = new KeyEvent(frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(VK_D);
        assertEquals(3, keyhandler.getAddx());

        KeyEvent VK_A = new KeyEvent(frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(VK_A);
        assertEquals(-3, keyhandler.getAddx());

        KeyEvent VK_S = new KeyEvent(frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(VK_S);
        assertEquals(3, keyhandler.getAddy());
        keyhandler.keyReleased(VK_S);

        KeyEvent VK_T = new KeyEvent(frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_T, KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(VK_T);
        assertEquals(0, keyhandler.getAddy());
        assertEquals(0, keyhandler.getAddx());

    }
}
