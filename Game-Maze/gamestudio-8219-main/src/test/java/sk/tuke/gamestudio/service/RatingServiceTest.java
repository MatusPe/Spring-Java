package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

public class RatingServiceTest {
    @Test
    void resetTEST(){RatingService ratingService=new RatingServiceJDBC();
        ratingService.setRating(new Rating("Jan", "Maze", 3, new Date()));
        ratingService.reset();
        assertEquals(0, ratingService.getAverageRating("Maze"));
    }
    @Test
    void changeRate(){
        RatingService ratingService=new RatingServiceJDBC();
        ratingService.reset();
        ratingService.setRating(new Rating("Stefan", "Maze", 5, new Date()));
        ratingService.setRating(new Rating("Richard", "Maze", 3, new Date()));
        ratingService.setRating(new Rating("Richard", "Maze", 1, new Date()));
        assertEquals(5, ratingService.getRating("Stefan", "Maze"));
        assertEquals(1, ratingService.getRating("Richard", "Maze"));
    }
    @Test
    void addRate(){
        RatingService ratingService=new RatingServiceJDBC();
        ratingService.reset();
        ratingService.setRating(new Rating("Jan", "Maze", 5, new Date()));
        ratingService.setRating(new Rating("Peter", "Maze", 3, new Date()));
        assertEquals(5, ratingService.getRating("Jan", "Maze"));
        assertEquals(3, ratingService.getRating("Peter", "Maze"));
    }
    @Test
    void getAvarageRatingTEST(){
        RatingService ratingService=new RatingServiceJDBC();
        ratingService.reset();
        ratingService.setRating(new Rating("Jan", "Maze", 4, new Date()));
        ratingService.setRating(new Rating("Ivan", "Maze", 2, new Date()));
        ratingService.setRating(new Rating("Tamia", "Maze", 5, new Date()));
        ratingService.setRating(new Rating("Peter", "Maze", 3, new Date()));
        ratingService.setRating(new Rating("Tamia", "Maze", 3, new Date()));
        ratingService.setRating(new Rating("Peter", "mines", 5, new Date()));
        assertEquals(3, ratingService.getAverageRating("Maze"));
    }
}
