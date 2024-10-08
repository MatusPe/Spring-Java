package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);
    List<Score>getTopScores(String game);
    void reset();
    public int getRating(String game, String player);
}
