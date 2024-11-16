package sk.tuke.kpi.kp.ninemensmorris.service.comment;

import sk.tuke.kpi.kp.ninemensmorris.entity.Comment;
import sk.tuke.kpi.kp.ninemensmorris.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService
{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addComment(Comment comment)
    {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game)
    {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.game = :game", Comment.class)
                .setParameter("game", game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset()
    {
        entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
