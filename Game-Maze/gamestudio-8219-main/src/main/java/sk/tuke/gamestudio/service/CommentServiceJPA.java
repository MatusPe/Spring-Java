package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Transactional

public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return entityManager.createQuery("select s from Comment s where s.game=:game order by s.player_at desc ", Comment.class).setParameter("game", game).getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE from comment").executeUpdate();
    }
}
