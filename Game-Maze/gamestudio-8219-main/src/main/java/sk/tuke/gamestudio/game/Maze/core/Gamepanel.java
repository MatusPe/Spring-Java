package sk.tuke.gamestudio.game.Maze.core;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.interaction.UserInteraction;
import sk.tuke.gamestudio.service.*;

import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

import static sk.tuke.gamestudio.game.Maze.core.game.GAME_LOSE;
import static sk.tuke.gamestudio.game.Maze.core.game.GAME_PLAYING;

public class Gamepanel extends JPanel implements Runnable{

    final int tilesSile=16;
    final int scale=3;
    public final int size=tilesSile*scale;

    private keyhandler keyhandler=new keyhandler();
    private String Username;

    private Thread thread;
    private Scanner scanner=new Scanner(System.in);

    private mazeMap mazeMap;

    private int number;
    private int score;


    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;;

    private UserInteraction userInteraction=new UserInteraction();

    private Commander commander;
    private JFrame windows;

    game game= GAME_PLAYING;
    public Gamepanel(){

    }

    public void StartGame(){

        System.out.println("Username:");
        this.Username=scanner.nextLine();
        this.thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        userService.save(new User("Jan", "heslo", "peter..."));
        userService.save(new User("Jan", "heslo", "peter..."));
        userService.save(new User("Jan", "heslo", "peter..."));
        userService.save(new User("Peter", "heslo", "peter..."));
        userService.save(new User("Richard", "heslo", "peter..."));
        userService.save(new User("Riso", "heslo", "peter..."));

        System.out.println(userService.findByUsername("Richard", "heslo"));
        System.out.println(userService.findByUsername("Ivan", "heslo"));

        double drawInterval=1000000000/60;
        double delta=0;
        score=2000*number;
        long lastTime=System.nanoTime();
        long currentTime;
        while (thread!=null){
            currentTime=System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            lastTime=currentTime;
            if(score<=0){
                thread=null;
                game=GAME_LOSE;

            }

            if(delta>=1){
                score--;
                update();
                repaint();
                delta--;

            }
        }

        try {
            scoreService.addScore(new Score(Username, "Maze", score, new Date()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Decoration and text
        userInteraction.AfterWin(Username,score);
        //Score add and display
        int number1=1;
        for(Score playerScore:scoreService.getTopScores("Maze")){
            System.out.println(String.format("           %d %s %s %d %s",number1++, playerScore.getPlayer(),playerScore.getGame(),playerScore.getPoint(),playerScore.getPlayerAt()));
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
        System.out.println("Would you like give us Comment? (Yes/No)");
        //comment add
        if(Objects.equals(scanner.nextLine().toUpperCase(Locale.ROOT), "YES")) {
            String Comment = scanner.nextLine();
            commentService.addComment(new Comment(Username, "Maze", Comment, new Date()));
        }
        //priemerna hodnota pouzivatelov
        System.out.println("Priemerne hodnotenie použivatelov "+ratingService.getAverageRating("Maze"));
        System.out.println("Would you like give us Rating? (Yes/No)");
        // add rating
        if(Objects.equals(scanner.nextLine().toUpperCase(Locale.ROOT), "YES")) {
            int rating = Integer.parseInt(scanner.nextLine());
            ratingService.setRating(new Rating(Username, "Maze", rating, new Date()));
        }
        //vyhladanie pouzivatela
        System.out.println("Hodnotenie pouzivatela lp: "+ratingService.getRating("Maze", "lp"));

        //vypisanie pouzivatela
        System.out.println("Komentare:");
        for (Comment comment : commentService.getComments("Maze")) System.out.println(String.format("           %d %s %s %s %s",number1++, comment.getPlayer(),comment.getGame(),comment.getComment(),comment.getPlayer_at()));;

        System.exit(0);

    }
    public void update(){
        commander.update();
    }
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D) g;

        mazeMap.draw(g2, commander);
        commander.draw(g2);

        g2.dispose();
    }

    public void inicialization(){

        //vytvorenie okna a pripravenie potrebnych prvkou na hru
        this.windows = new JFrame("My Frame");

        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setResizable(false);
        windows.setTitle("Maze");

        this.number= userInteraction.Mazewidth();

        this.mazeMap=new mazeMap(number/2, this);
        this.commander=new Commander(this, keyhandler, mazeMap);
        this.setPreferredSize(new Dimension(size*number,size*number));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyhandler);
        this.setFocusable(true);

        windows.add(this);
        windows.pack();

        windows.setLocationRelativeTo(null);
        windows.setVisible(true);
        this.StartGame();
    }



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public UserInteraction getUserInteraction() {
        return userInteraction;
    }

    public void setUserInteraction(UserInteraction userInteraction) {
        this.userInteraction = userInteraction;
    }
}
