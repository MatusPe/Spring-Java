package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService{
    private String url="http://localhost:8080/api/comment";

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void addComment(Comment comment) {
        restTemplate.postForEntity(url, comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) {
        return Arrays.asList(restTemplate.getForEntity(url+"/"+game, Comment[].class).getBody());
    }

    @Override
    public void reset() {
        throw  new UnsupportedOperationException("reset is not supported");
    }
}
