package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long ident;

    private String player;
    private String game;
    private String comment;
    private Date player_at;
    public Comment(){

    }
    public Comment(String player, String game, String comment, Date playedAt){
        this.player=player;
        this.game=game;
        this.comment=comment;
        this.player_at =playedAt;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPlayer_at() {
        return player_at;
    }

    public void setPlayer_at(Date date) {
        this.player_at = date;
    }
    @Override
    public String toString() {
        return "Comment{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + player_at +
                '}';
    }
}
