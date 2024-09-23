package sk.tuke.gamestudio.game.Maze.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static sk.tuke.gamestudio.game.Maze.core.game.GAME_PLAYING;

public class mazeMap {

    private static final int[][] NEIGHBOR_OFFSETS_EVEN = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };
    private static final int[][] NEIGHBOR_OFFSETS_ODD = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };
    //private final int width;
    private final int height;
    private final boolean[][] visited;
    private final List<int[]> walls;


    private  char[][]maze;
    private Gamepanel gamepanel;
    private int Tile;
    public mazeMap( int height){

        this.height = height;
        this.maze=new char[2 * height + 1][2 * height + 1];
        visited = new boolean[height][height];
        walls = new ArrayList<>();
        generateMaze(0, 0);


    }

    public mazeMap( int height, Gamepanel gamepanel) {
        this.gamepanel=gamepanel;
        this.height = height;
        this.maze=new char[2 * height + 1][2 * height + 1];
        visited = new boolean[height][height];
        walls = new ArrayList<>();

        this.Tile= gamepanel.size;
        generateMaze(0, 0);



    }

    private void generateMaze(int row, int col) {
        visited[row][col] = true;

        int[][] neighborOffsets = (col % 2 == 0) ? NEIGHBOR_OFFSETS_EVEN : NEIGHBOR_OFFSETS_ODD;
        Collections.shuffle(Arrays.asList(neighborOffsets));

        for (int[] offset : neighborOffsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            if (isValid(newRow, newCol) && !visited[newRow][newCol]) {
                walls.add(new int[]{row, col, newRow, newCol});
                generateMaze(newRow, newCol);
            }
        }
        for (int i = 0; i < 2 * height + 1; i++) {
            for (int j = 0; j < 2 * height + 1; j++) {
                maze[i][j] = '#';
            }
        }


        for (int[] wall : walls) {
            int row1 = 2 * wall[0] + 1;
            int col1 = 2 * wall[1] + 1;
            int row2 = 2 * wall[2] + 1;
            int col2 = 2 * wall[3] + 1;
            maze[row1][col1] = ' ';
            maze[row2][col2] = ' ';
            maze[(row1 + row2) / 2][(col1 + col2) / 2] = ' ';

        }
        this.maze[2 * height-1][2 * height-1]='$';
        this.maze[1][1]='1';
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < height;
    }

    public void printMaze(Commander commander) {
        int x=commander.getX();
        int y=commander.getY();
        this.maze[x/48][y/48]='1';
        int index=0;
        for(int i=0;i<2 * height + 1;i++){

            while(2 * height + 1!=index){
                System.out.printf(" ═══");
                index++;
            }
            index=0;
            System.out.println();
            for(int j=0; j<2 * height + 1;j++){
                if(j<x / 48 + 2&&j>=x / 48 - 1&&i<y / 48 + 2&&i>=y / 48 - 1||maze[j][i]=='$'||gamepanel.game!=GAME_PLAYING){
                    System.out.printf(String.valueOf("║ "+"\u001B[31m"+maze[j][i]+"\u001B[0m")+" ");
                }else{
                    System.out.printf(String.valueOf("║ "+"* "));
                }
            }
            System.out.println("║");
        }
        //index=0;
        while(2 * height + 1!=index){
            System.out.printf(" ═══");
            index++;
        }
        System.out.println();
        this.maze[x/48][y/48]=' ';
    }

    public void draw(Graphics2D g2, Commander commander){

        BufferedImage image = null;
        int x;
        int y;
        int limitOfSight;
        if(gamepanel.game== GAME_PLAYING) {
            x = (commander.getX() - 48) / 48;
            y = (commander.getY() - 48) / 48;
            limitOfSight=3;
        }else{
            printMaze(commander);
            x=0;
            y=0;
            limitOfSight=2 * height+1;
        }
        for (int i = x; i < x+limitOfSight; i++) {
            for (int j = y; j < y+limitOfSight; j++) {
                try {
                    if(this.maze[i][j]==' '||this.maze[i][j]=='1'||this.maze[i][j]=='$') {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/images/floor01.png")));
                    }else if (this.maze[i][j]=='#'){
                         image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/images/wall.png")));
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
                g2.drawImage(image, i*Tile, j*Tile, Tile, Tile, null);
            }

        }
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/static/images/flag.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(image, (2 * this.height-1)*Tile, (2 * this.height-1)*Tile, Tile, Tile, null);
    }
    public char[][] getMaze() {
        return maze;
    }

    public void setMaze(char[][] maze) {
        this.maze = maze;
    }

    public void setMazeTitle(int oldx, int oldy, int row, int column, char player){
        maze[oldx][oldy]=' ';
        maze[row][column]='1';
    }

}