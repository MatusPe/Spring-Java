package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;
    private int newj;
    @Override
    public void setRating(Rating rating) {
        int isthere=getRating(rating.getGame(), rating.getPlayer());
        System.out.println(isthere);
        if(newj==0){
            System.out.println(rating.getGame());
            entityManager.persist(rating);
        }else if (rating.getRating()!=0){
            System.out.println(rating.getGame());
            System.out.println(rating.getPlayer());
            System.out.println(rating.getPlayedat());
            System.out.println(rating.getRating());
            System.out.println("ok");


            entityManager.createQuery("UPDATE Rating SET rating_by_player=:rating_by_player WHERE game=:game and   player=:player")
                    .setParameter("rating_by_player", rating.getRating()).setParameter("game", rating.getGame()).setParameter("player", rating.getPlayer()).executeUpdate();}


    }

    @Override
    public double getAverageRating(String game) {


        Query query = entityManager.createQuery("SELECT AVG(r.rating_by_player) FROM Rating r WHERE r.game = :game");
        query.setParameter("game", game);
        Double averageRating = query.getSingleResult() == null ? 0 : (Double) query.getSingleResult();

        return  Math.round(averageRating*10.0)/10.0;



    }

    @Override
    public int getRating(String game, String player) {
        Query query = entityManager.createQuery("SELECT r.rating_by_player FROM Rating r WHERE r.game=:game and r.player=:player");
        query.setParameter("game", game);
        query.setParameter("player", player);





        List<Integer> resultList = query.getResultList();


        if (resultList.isEmpty()) {
            System.out.println("ok");
            return 0;
        } else {
            // Vrátí první nalezený výsledek

            newj=resultList.size();
            return resultList.get(0);
        }
    }



    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE from rating").executeUpdate();
    }
}
