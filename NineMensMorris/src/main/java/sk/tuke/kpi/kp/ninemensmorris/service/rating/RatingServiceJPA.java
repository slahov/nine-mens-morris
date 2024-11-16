package sk.tuke.kpi.kp.ninemensmorris.service.rating;

import sk.tuke.kpi.kp.ninemensmorris.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService
{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void setRating(Rating rating)
    {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game)
    {
        Double averageRating = (Double) entityManager.createQuery("SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
                .setParameter("game", game)
                .getSingleResult();
        return averageRating != null ? averageRating.intValue() : 0;
    }

    @Override
    public Rating getRating(String game, String player)
    {
        return entityManager.createQuery("SELECT r FROM Rating r WHERE r.game = :game AND r.player = :player", Rating.class)
                      .setParameter("game", game).setParameter("player", player)
                      .getSingleResult();
    }

    @Override
    public void reset()
    {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();
    }
}
