package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService{
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_password = "Jjdjsjkk";
    public static final String INSERT_COMMENT = "INSERT INTO comment(player, game, comment, player_at) values (?, ?, ?, ?)";
    public static final String SELECT_VALUE = "SELECT player, game, comment, player_at from comment where game=? order by player_at desc";

    @Override
    public void addComment(Comment comment) {
        try (var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER,JDBC_password); var statement=connection.prepareStatement(INSERT_COMMENT)){

            statement.setString(1, comment.getPlayer());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getPlayer_at().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try (var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER,JDBC_password);
             var statement=connection.prepareStatement(SELECT_VALUE)){


            statement.setString(1,game);

            try (var rs=statement.executeQuery()){
                var listcomment=new ArrayList<Comment>();
                while (rs.next()){
                    listcomment.add(new Comment(rs.getString(1), rs.getString(2),rs.getString(3), rs.getTimestamp(4)));
                }
                return listcomment;
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reset() {
        try (var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER,JDBC_password); var statement=connection.createStatement()){
            statement.executeUpdate("delete from comment");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
