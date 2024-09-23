package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static sk.tuke.gamestudio.service.RatingServiceJDBC.SELECT_VALUE;

public class ScoreServiceJDBC implements ScoreService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_password = "Jjdjsjkk";
    public static final String SELECT_SCORE = "select player, game, point, player_at from score where game=? order by point desc limit 10";
    public static final String INSERT_SCORE = "insert into score(player, game, point, player_at) values (?,?,?,?)";

    @Override
    public void addScore(Score score) {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_password);
            var statement=connection.prepareStatement(INSERT_SCORE)){
            statement.setString(1,score.getPlayer());
            statement.setString(2,score.getGame());
            statement.setInt(3,score.getPoint());
            statement.setTimestamp(4,new Timestamp(score.getPlayerAt().getTime()));
            statement.executeUpdate();


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Score> getTopScores(String game) {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_password);
            var statement=connection.prepareStatement(SELECT_SCORE)){
            statement.setString(1, game);
            try ( var rs=statement.executeQuery()){

                var score=new ArrayList<Score>();
                while (rs.next()){
                    score.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return score;
            }



        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void reset() {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_password);
            var statement=connection.createStatement()){
            statement.executeUpdate("delete from score");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
    }
    @Override
    public int getRating(String player, String game) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_password);
             var statement = connection.prepareStatement(SELECT_VALUE)) {
            statement.setString(1, player);
            statement.setString(2, game);

            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return rs.getInt(1);

            }
        } catch (SQLException throwables) {
            System.err.println("Problem selecting rating");
            System.err.println("Rating can not be loaded.");
            System.err.println(throwables.getMessage());
        }
        return -1;

    }
}

