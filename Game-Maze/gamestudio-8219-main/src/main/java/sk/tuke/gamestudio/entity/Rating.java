package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;
@Entity

public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int ident;
    private String player;
    private String game;
    private int rating_by_player;
    private Date rateon;
    public Rating(){

    }
    public Rating(String player, String game, int rating_by_player, Date rateon){
        this.player=player;
        this.game=game;
        this.rating_by_player=rating_by_player;
        this.rateon=rateon;


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

    public int getRating() {
        return rating_by_player;
    }

    public void setRating(int rating) {
        this.rating_by_player = rating;
    }

    public Date getPlayedat() {
        return rateon;
    }

    public void setPlayedat(Date playedat) {
        this.rateon = playedat;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", rating=" + rating_by_player +
                ", playedat=" + rateon +
                '}';
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
