package sk.tuke.gamestudio.service;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class CommentServiceTest {
    @Test
    void resetTEST(){
        /*CommentService commentService=new CommentServiceJDBC();
        commentService.addComment(new Comment("Jan", "Maze", "Dobra hra", new Date()));
        commentService.reset();
        //commentService.addComment(new Comment("Jan", "Maze", "Dobra hra", new Date()));
        assertEquals(0, commentService.getComments("Maze").size());*/
    }
    @Test
    void addTest(){
        CommentService commentService=new CommentServiceJDBC();
        commentService.reset();
        var date=new Date();
        commentService.addComment(new Comment("Peter", "Maze", "Toto je komentar", date));
        assertEquals(1, commentService.getComments("Maze").size());
        commentService.addComment(new Comment("Ivan", "Maze", "Toto je komentar 2", date));
        var comment=commentService.getComments("Maze");
        assertEquals("Peter", comment.get(0).getPlayer());
        assertEquals("Maze", comment.get(0).getGame());
        assertEquals("Toto je komentar", comment.get(0).getComment());
        assertEquals(date, comment.get(0).getPlayer_at());
        //player2
        assertEquals("Ivan", comment.get(1).getPlayer());
        assertEquals("Maze", comment.get(1).getGame());
        assertEquals("Toto je komentar 2", comment.get(1).getComment());
        assertEquals(date, comment.get(1).getPlayer_at());
    }
    @Test
    void getCommentTEST(){
        CommentService commentService=new CommentServiceJDBC();
        commentService.reset();
        commentService.addComment(new Comment("Peter", "Maze", "Toto je komentar", new Date(122, 2,1,10,30,0)));

        commentService.addComment(new Comment("Ivan", "Maze", "Toto je komentar 2", new Date(124, 2,1,10,30,0)));
        assertEquals(2, commentService.getComments("Maze").size());
        var comment=commentService.getComments("Maze");
        //player1
        assertEquals("Ivan", comment.get(0).getPlayer());
        assertEquals("Maze", comment.get(0).getGame());
        assertEquals("Toto je komentar 2", comment.get(0).getComment());
        assertEquals(new Date(124, 2,1,10,30,0), commentService.getComments("Maze").get(0).getPlayer_at());
        //player2
        assertEquals("Peter", comment.get(1).getPlayer());
        assertEquals("Maze", comment.get(1).getGame());
        assertEquals("Toto je komentar", comment.get(1).getComment());
        assertEquals(new Date(122, 2,1,10,30,0), commentService.getComments("Maze").get(1).getPlayer_at());

    }
}
