package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService{
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_password = "Jjdjsjkk";
    public static final String INSERT_COMMENT = "INSERT INTO comment(player, game, comment, Data) values (? ? ? ?)";
    public static final String SELECT_VALUE = "SELECT rating_by_player from rating where player=? and game=?;";
    public static final String SELECT_VALUE1 = "SELECT rating_by_player from rating where game=?;";

    public final String JDBC_INSERT_RATING = "INSERT INTO rating(player,game, rating_by_player, rateon) VALUES(?,?,?, ?) ON CONFLICT (ident)DO UPDATE set  rating_by_player = ?, rateon = ?;";

    @Override
    public void setRating(Rating rating) {
        try (var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_password);var statement=connection.prepareStatement(JDBC_INSERT_RATING)){
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());

            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getPlayedat().getTime()));
            statement.setInt(5, rating.getRating());
            statement.setTimestamp(6, new Timestamp(rating.getPlayedat().getTime()));

            statement.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getAverageRating(String game) {
        try (var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_password);var statement=connection.prepareStatement(SELECT_VALUE1)){
            int avarage=0;
            double count=0;
            statement.setString(1, game);

            try (var rs=statement.executeQuery()){

                while ((rs.next())){

                    avarage+=rs.getInt(1);
                    count++;
                }

                //System.out.println(avarage);
                return (double) (avarage/count);

            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void reset() {
        try (var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_password);var statement=connection.createStatement()){
            statement.executeUpdate("DELETE from rating");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
