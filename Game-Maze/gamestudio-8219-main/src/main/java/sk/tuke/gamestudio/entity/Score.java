package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;
@Entity

public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long ident;
    private String player;

    private String game;

    private int point;

    private Date playerAt;
    public Score(){

    }

    public Score(String player, String game, int points, Date playedAt) {
        this.player = player;
        this.game = game;
        this.point = points;
        this.playerAt = playedAt;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int points) {
        this.point = points;
    }

    public Date getPlayerAt() {
        return playerAt;
    }

    public void setPlayerAt(Date playedAt) {
        this.playerAt = playedAt;
    }

    @Override
    public String toString() {
        return "Score{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", points=" + point +
                ", playedAt=" + playerAt +
                '}';
    }
}
