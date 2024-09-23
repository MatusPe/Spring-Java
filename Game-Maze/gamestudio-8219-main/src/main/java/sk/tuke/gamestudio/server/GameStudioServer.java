package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Configuration

@EnableSwagger2
@EntityScan(basePackages = "sk.tuke.gamestudio.entity")
public class GameStudioServer {
    public static void main(String[] args) {

        SpringApplication.run(GameStudioServer.class);
    }
    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }
    @Bean CommentService commentService(){
        return new CommentServiceJPA();
    }
    @Bean RatingService ratingService(){
        return new RatingServiceJPA();
    }
    @Bean UserService userService(){return new UserServiceJPA();}
    @Bean PlayerMove playerMove(){return new PlayerMoveServiceJPA();}






}
