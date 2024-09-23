package sk.tuke.gamestudio.game.Maze;



import sk.tuke.gamestudio.game.Maze.core.Commander;
import sk.tuke.gamestudio.game.Maze.core.Gamepanel;
import sk.tuke.gamestudio.game.Maze.core.mazeMap;


public class Main {
    public static void main(String[] args) {



        mazeMap map=new mazeMap(9/2);
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                System.out.print(map.getMaze()[j][i]);
            }
            System.out.println();
        }

    }
}
