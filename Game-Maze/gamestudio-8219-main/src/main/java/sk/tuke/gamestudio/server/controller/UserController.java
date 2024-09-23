package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.*;
import sk.tuke.gamestudio.game.Maze.core.mazeMap;
import sk.tuke.gamestudio.service.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    @Autowired
    private UserService userService;
    private boolean loggedIn = false;
    private String username;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private ScoreService scoreService;


    @Qualifier("playerMove")
    @Autowired
    private PlayerMove playMove;

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/Registration")
    public String Registration(String login, String password, String Email){
        if(userService.findByUsername(login, password)){
            System.out.println(login);
            return null;
        }
        userService.save(new User(login, password, Email));

        System.out.println(login);
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(String login, String password){
        this.username = login;
        if(userService.findByUsername(login, password)==true){
            loggedIn=true;
            userService.setUser(this.username);
            System.out.println("logged in");
            scoreService.addScore(new Score(username, "Maze", 0, new Date()));
            System.out.println("score");
            ratingService.setRating(new Rating(username, "Maze", 0, new Date()));

            System.out.println("rating");
            return "Dificulty";

        }

        return "redirect:/";
    }
    @RequestMapping("/RatingandComment")
    public String RatingandComment(String comment,Integer rating){
        System.out.println(this.username+"toto je meno");
    if(comment!=null){
        commentService.addComment(new Comment(username, "Maze", comment, new Date()));
    }if(rating>0){
        System.out.println("rating");
        System.out.println(comment);
        System.out.println(rating);
        System.out.println("rating");
        ratingService.setRating(new Rating(username,"Maze", rating, new Date()));
    }return "Mazes";

    }
    @RequestMapping("/AvarageRatingand")
    @ResponseBody
    public Double AvarageRating(){
        return ratingService.getAverageRating("Maze");
    }


    @RequestMapping("/logout")
    public String logout(){
        this.loggedIn = false;
        return "index";
    }
    @RequestMapping("/MyScore")
    @ResponseBody
    public String GetmyScoreandRating(){
        StringBuilder score=new StringBuilder();
        score.append("Me:  <br> "+username+"<br>"+"Rating:"+ratingService.getRating("Maze", username.toString())+"<br>"+"Score:"+ scoreService.getRating("Maze", username));
        return score.toString();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }




}
