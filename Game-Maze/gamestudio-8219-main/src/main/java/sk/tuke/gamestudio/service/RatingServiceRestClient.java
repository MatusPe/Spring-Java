package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;
@RestController
public class RatingServiceRestClient implements RatingService{

    private String url="http://localhost:8080/api/rating";
    //@SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        try {
            System.out.println(rating);
            restTemplate.postForEntity(url, rating, Rating.class);
        }catch (Exception e) {


            System.out.println(e);
        }
    }

    @Override
    public double getAverageRating(String game) {
        return restTemplate.getForEntity(url+'/'+game, Double.class ).getBody();
    }

    @Override
    public int getRating(String game, String player) {
        return restTemplate.getForEntity(url+'/'+game+'/'+player, Integer.class ).getBody();
    }

    @Override
    public void reset() {
        throw  new UnsupportedOperationException("reset is not supported");
    }
}
