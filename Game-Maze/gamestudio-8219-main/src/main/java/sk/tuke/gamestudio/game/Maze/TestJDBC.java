package sk.tuke.gamestudio.game.Maze;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;

public class TestJDBC {
    public static void main(String[] args) throws Exception {


        ScoreService scoreService=new ScoreServiceJDBC();
        scoreService.reset();
        scoreService.addScore(new Score("jaro","Maze", 105, new Date()));

        System.out.println("aadad");
    }
}
