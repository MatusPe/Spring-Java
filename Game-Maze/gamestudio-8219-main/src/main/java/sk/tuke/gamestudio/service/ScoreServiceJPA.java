package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional

public class ScoreServiceJPA implements ScoreService{

    @PersistenceContext
    private EntityManager entityManager;
    private int newj;
    @Override
    public void addScore(Score score) {



        int isthere=getRating(score.getGame(), score.getPlayer());
        System.out.println(isthere+"");
        if(newj==0){

            entityManager.persist(score);
        }else {

            System.out.println("ok");


            Query query = entityManager.createQuery("SELECT s.point FROM Score s WHERE s.game=:game and s.player=:player");
            query.setParameter("game", score.getGame());
            query.setParameter("player", score.getPlayer());
            List<Integer> resultList = query.getResultList();
            if (resultList.size() > 0) {
                Integer originalPoint = resultList.get(0);

// Pokud je nový bodový zisk větší než původní, provede se aktualizace
                if (score.getPoint() > originalPoint) {
                    entityManager.createQuery("UPDATE Score SET point=:point WHERE game=:game and player=:player")
                            .setParameter("point", score.getPoint())
                            .setParameter("game", score.getGame())
                            .setParameter("player", score.getPlayer())
                            .executeUpdate();
                }
            }
        }






    }

    @Override
    public List<Score> getTopScores(String game) {

        var scores = entityManager.createQuery("select s from Score s where s.game = :game order by s.point desc", Score.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
        return scores;


    }

    @Override
    public void reset() {
        try {
            entityManager.createNativeQuery("DELETE from score").executeUpdate();
        }catch (Exception throwables){
            System.err.println("Problem inserting score");
            System.err.println("Your score can not be loaded.");
            System.err.println(throwables.getMessage());
        }



    }
    @Override
    public int getRating(String game, String player) {
        Query query = entityManager.createQuery("SELECT r.point FROM Score r WHERE r.game=:game and r.player=:player");
        query.setParameter("game", game);
        query.setParameter("player", player);

        List<Integer> resultList = query.getResultList();

        // Pokud není nalezen žádný výsledek, vrať 0
        if (resultList.isEmpty()) {

            return 0;
        } else {
            // Vrátí první nalezený výsledek

            newj=resultList.size();
            return resultList.get(0);
        }
    }

}
