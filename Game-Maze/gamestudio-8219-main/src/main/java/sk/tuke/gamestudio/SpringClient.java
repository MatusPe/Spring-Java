package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.Maze.core.Gamepanel;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
@EnableTransactionManagement
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "sk.tuke.gamestudio.server.*"))

public class SpringClient {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringClient.class).headless(false).web(WebApplicationType.NONE).run(args);
        Gamepanel appFrame = context.getBean(Gamepanel.class);
    }
    @Bean
    public CommandLineRunner runner(Gamepanel gamepanel){
        return s-> {
            gamepanel.inicialization();
        };
    }
    @Bean
    public Gamepanel gamepanel(){
        return new Gamepanel();
    }
    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceRestClient();
    }
    @Bean
    public CommentService commentService(){
        return new CommentServiceRestClient();
    }
    @Bean
    public RatingService ratingService(){
        return new RatingServiceRestClient();
    }
    @Bean UserService userService(){return new UserServiceJPA();}

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }




}
