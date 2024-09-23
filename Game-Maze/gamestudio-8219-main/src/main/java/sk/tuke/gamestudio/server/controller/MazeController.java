package sk.tuke.gamestudio.server.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.PlayMove;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.Maze.core.StatusGame;
import sk.tuke.gamestudio.game.Maze.core.mazeMap;
import sk.tuke.gamestudio.service.PlayerMove;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;


@Controller
//@RequestMapping("/Mazes")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MazeController {
    int caount;
    private int sizeMaze = 9;
    private mazeMap mazeMap = new mazeMap(9 / 2);
    private int xPosition = 1;
    private int yposition = 1;
    private boolean isSolved = false;
    @Autowired
    private ScoreService scoreService;
    private String name;
    @Autowired
    private UserService userService;
    private boolean won=false;
    private StatusGame statusGame=StatusGame.Playing;

    private List<PlayMove> list;
    private int lengt;
    private int countMove=0;
    private Instant startime;
    private long score=1000;
    private int scoredifference;
    private boolean showall=false;
    private int check;

    @Qualifier("playerMove")
    @Autowired
    private PlayerMove playMove;

    @RequestMapping("/Mazes")
    public String Mazes(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column) {
        if (row != null && column != null) {
            if (row == 7 && column == 7) {
                if (!isSolved) {
                    isSolved = true;
                    mazeMap.setMazeTitle(xPosition, yposition, row, column, '1');
                    xPosition = row;
                    yposition = column;

                }
            } else if (!isSolved) {
                mazeMap.setMazeTitle(xPosition, yposition, row, column, '1');
                xPosition = row;
                yposition = column;
            }
            //model.addAttribute("scores", scoreService.getTopScores("Maze"));
            //mazeMap.setMazeTitle(row, column, '1');
        }


        return "Mazes";
    }


    @RequestMapping("/Mazes/newGame")

    public String NewGame() {
        startime=Instant.now();
        showall=false;
        this.mazeMap = new mazeMap(sizeMaze / 2);
        xPosition = 1;
        yposition = 1;
        this.isSolved = false;
        this.statusGame=StatusGame.Playing;
        score=1000*check;
        return "Mazes";
    }
    @PostConstruct
    public void init() {
        playMove.ResetDatabase();
        System.out.println("Session scope bean initialized.");
    }

    public String getCurrentTime() {
        return new Date().toString();
    }

    public String getHtmlMaze() {
        System.out.println("toto je pozicia x"+xPosition);
        System.out.println("toto je pozicia y"+yposition);

        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"maze2\">");
        sb.append("<table class=\"myTable\" >");
        for (int row = 0; row < sizeMaze; row++) {
            sb.append("<tr style=\"height: 40px; border: 0;\">"); // Set height for each row
            for (int column = 0; column < sizeMaze; column++) {
                sb.append("<td style=\"width: 40px; border: 0; padding: 0; margin: 0; font-size: 0;\">");
                sb.append("<a />\n");
                sb.append("<img src='/images/" + getTile(row, column) + ".png' width='45' height='45'>");
                sb.append("</a>\n");
                sb.append("</td > ");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");

        System.out.println("nove");
        sb.append("</div>");


        return sb.toString();
    }
    @PreDestroy
    public void cleanUpDatebase(){
        playMove.ResetDatabase();
    }


    @RequestMapping(value = "/Mazes/Maze", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String Mazes(@RequestParam(required = false) String row, @RequestParam(required = false) String column) {
        if (row != null && column != null) {

            //mazeMap.getMaze()[Integer.parseInt(row)][Integer.parseInt(column)] = '1';
            mazeMap.getMaze()[xPosition][yposition] = ' ';
            mazeMap.getMaze()[Integer.parseInt(row)][Integer.parseInt(column)] = '1';
            System.out.println(xPosition);
            System.out.println(yposition);
            xPosition = Integer.parseInt(row);
            yposition = Integer.parseInt(column);


        }
        System.out.println("adasdsa");
        return getHtmlMaze();
    }

    @PostMapping("/keypress")
    @ResponseBody
    public String handleKeyPress(@RequestBody(required = false) KeyPressData keyPressData) {

        System.out.println("lalla");
        if(startime==null){
            startime = Instant.now();
        }
        if(statusGame==StatusGame.Playing) {
            String keyPressed = keyPressData.getKey();
            System.out.println("Key Pressed: " + keyPressed);
            if (Objects.equals(keyPressed, "a") || Objects.equals(keyPressed, "A")) {
                //yposition-=1;
                CheckWall(xPosition, yposition - 1);
            } else if (Objects.equals(keyPressed, "s") || Objects.equals(keyPressed, "S")) {
                //xPosition+=1;
                CheckWall(xPosition + 1, yposition);
            } else if (Objects.equals(keyPressed, "w") || Objects.equals(keyPressed, "W")) {
                //xPosition-=1;
                CheckWall(xPosition - 1, yposition);
            } else if (Objects.equals(keyPressed, "d") || Objects.equals(keyPressed, "D")) {
                //yposition+=1;
                CheckWall(xPosition, yposition + 1);
            }
            if(xPosition == sizeMaze-2 && yposition == sizeMaze-2) {
                this.statusGame=StatusGame.Won;
                showall=true;
                this.list = playMove.ListofMove(userService.getUser());
                this.lengt=list.size();
                score/= ((Duration.between(startime, Instant.now()).getSeconds())/2);
                System.out.println(score);
                System.out.println("toto je score");
                if(score>0) {
                    scoreService.addScore(new Score(userService.getUser(), "Maze", (int) score, new Date()));
                }
                score=1000*check;



            }

            //xPosition+=1;
            System.out.println(userService.getUser() + "to je skveleee");
            System.out.println(xPosition);
        }

        return "Key press received";
    }

    @RequestMapping("/larat")
    public String Larat() {
        return "Mazes";
    }

    public void CheckWall(int rows, int columns) {
        if (mazeMap.getMaze()[rows][columns] == ' ' || mazeMap.getMaze()[rows][columns] == '$') {
            mazeMap.getMaze()[xPosition][yposition] = ' ';

            xPosition = rows;
            yposition = columns;
            mazeMap.getMaze()[rows][columns] = '1';
            playMove.savemove(new PlayMove(userService.getUser().toString(), xPosition, yposition));
            this.countMove=countMove+1;
        }

    }


    public char[][] Maze() {
        return mazeMap.getMaze();
    }



    @GetMapping("/Mazes/maze")
    public String getMazeJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(mazeMap.getMaze());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTile(int row, int column) {
        if (((row >= xPosition - 1 && row <= xPosition + 1) && (column >= yposition - 1 && column <= yposition + 1))||showall==true||sizeMaze-2==row&&sizeMaze-2==column) {
            switch (mazeMap.getMaze()[row][column]) {
                case '#':

                    return "wall";

                case ' ':
                    return "floor01";

                case '$':
                    return "flag";

                case '1':
                    return "A";

                default:
                    return null;
            }
        }
        return "black";
    }

    @RequestMapping("/Mazes/difficulty")
    public String dificult(String difficulty) {
        statusGame=StatusGame.Playing;
        lengt=0;
        showall=false;
        xPosition=1;
        yposition=1;
        if (difficulty.equals("Easy")) {
            this.sizeMaze = 9;
            scoredifference=1;
            check=6;
            score*=check;
        } else if (difficulty.equals("Medium")) {
            this.sizeMaze = 11;
            check=8;
            score*=check;
        } else if (difficulty.equals("Hard")) {
            this.sizeMaze = 13;
            check=9;
            score*=check;

        } else if (difficulty.equals("Nightmare")) {
            this.sizeMaze = 15;
            check=10;
            score*=check;
        }
        System.out.println(this.sizeMaze);
        this.mazeMap = new mazeMap(this.sizeMaze / 2);
        return "Mazes";
    }
    @RequestMapping("/endGame")
    public String endGame (){
        showall=true;
        this.statusGame=StatusGame.Won;
        this.list = playMove.ListofMove(userService.getUser());
        this.lengt=list.size();
        return "Mazes";
    }

    @PostMapping("/Arrow-leftPressed")
    public String getArrowLeftPressed(@RequestBody(required = false) ClickRequest clickRequest) {


        if(statusGame==StatusGame.lose||statusGame==StatusGame.Won) {
            this.mazeMap.getMaze()[xPosition][yposition] = ' ';
            if(clickRequest.clicked){
                System.out.println("clicked");
                this.lengt=lengt<list.size()?lengt+1:lengt;
                xPosition = list.get(lengt).getRow();
                yposition = list.get(lengt).getCol();



                System.out.println("lengt: "+lengt+"XPOSITION"+xPosition+"YPOSITION"+yposition);
                this.mazeMap.getMaze()[xPosition][yposition] = '1';
            }
            if(!clickRequest.clicked){
                System.out.println("not clicked");
                this.lengt=(lengt>(lengt-countMove-1)&&lengt>0)?lengt-1:lengt;
                xPosition = list.get(lengt).getRow();
                yposition = list.get(lengt).getCol();

                System.out.println("lengt: "+lengt+"XPOSITION"+xPosition+"YPOSITION"+yposition);
                this.mazeMap.getMaze()[xPosition][yposition] = '1';
            }

            System.out.println(clickRequest.clicked + "toto je click");



        }
        return "Mazes";
    }





    static class KeyPressData {
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    static class ClickRequest {
        private boolean clicked;

        public boolean isClicked() {
            return clicked;
        }

        public void setClicked(boolean clicked) {
            this.clicked = clicked;
        }
    }









}
