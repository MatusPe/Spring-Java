package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

public class ScoreServiceTest {


    @Test
    void resetTEST(){

        ScoreService scoreService=new ScoreServiceJDBC();
        scoreService.addScore(new Score("Matus", "Maze", 150, new Date()));
        scoreService.addScore(new Score("Jan", "Maze", 160, new Date()));
        scoreService.addScore(new Score("Martin", "Maze", 120, new Date()));
        scoreService.addScore(new Score("Ondrej", "mines", 110, new Date()));
        scoreService.addScore(new Score("Sabina", "Maze", 120, new Date()));
        scoreService.reset();
        assertEquals(0, scoreService.getTopScores("Maze").size());



    }
    @Test
    void addScoreTEST(){

        ScoreService scoreService=new ScoreServiceJDBC();
        scoreService.reset();
        var date=new Date();
        scoreService.addScore(new Score("Matus", "Maze", 150, date));
        scoreService.addScore(new Score("Jan", "Maze", 160, date));
        List<Score> scoreList = scoreService.getTopScores("Maze");
        assertEquals(2, scoreList.size());
        //player 1
        assertEquals("Matus", scoreService.getTopScores("Maze").get(1).getPlayer());
        assertEquals("Maze", scoreService.getTopScores("Maze").get(1).getGame());
        assertEquals(150, scoreService.getTopScores("Maze").get(1).getPoint());
        assertEquals(date, scoreService.getTopScores("Maze").get(1).getPlayerAt());
        //player 2
        assertEquals("Jan", scoreService.getTopScores("Maze").get(0).getPlayer());
        assertEquals("Maze", scoreService.getTopScores("Maze").get(0).getGame());
        assertEquals(160, scoreService.getTopScores("Maze").get(0).getPoint());
        assertEquals(date, scoreService.getTopScores("Maze").get(0).getPlayerAt());

    }
    @Test
    void topListTEST(){
        ScoreService scoreService=new ScoreServiceJDBC();
        scoreService.reset();
        var date=new Date();
        scoreService.addScore(new Score("Zuzana", "Maze", 120, date));
        scoreService.addScore(new Score("Peter", "mines", 110, date));
        scoreService.addScore(new Score("Gregor", "Maze", 160, date));
        scoreService.addScore(new Score("Ondrej", "mines", 180, date));
        scoreService.addScore(new Score("Sabina", "Maze", 100, date));
        assertEquals(3, scoreService.getTopScores("Maze").size());
        //player1
        assertEquals("Gregor", scoreService.getTopScores("Maze").get(0).getPlayer());
        assertEquals("Maze", scoreService.getTopScores("Maze").get(0).getGame());
        assertEquals(160, scoreService.getTopScores("Maze").get(0).getPoint());
        assertEquals(date, scoreService.getTopScores("Maze").get(0).getPlayerAt());
        //player2
        assertEquals("Zuzana", scoreService.getTopScores("Maze").get(1).getPlayer());
        assertEquals("Maze", scoreService.getTopScores("Maze").get(1).getGame());
        assertEquals(120, scoreService.getTopScores("Maze").get(1).getPoint());
        assertEquals(date, scoreService.getTopScores("Maze").get(1).getPlayerAt());
        //player3
        assertEquals("Sabina", scoreService.getTopScores("Maze").get(2).getPlayer());
        assertEquals("Maze", scoreService.getTopScores("Maze").get(2).getGame());
        assertEquals(100, scoreService.getTopScores("Maze").get(2).getPoint());
        assertEquals(date, scoreService.getTopScores("Maze").get(2).getPlayerAt());
    }

}
