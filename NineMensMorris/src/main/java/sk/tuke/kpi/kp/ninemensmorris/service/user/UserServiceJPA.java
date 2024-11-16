package sk.tuke.kpi.kp.ninemensmorris.service.user;

import sk.tuke.kpi.kp.ninemensmorris.entity.Uzer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Transactional
public class UserServiceJPA implements UserService
{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addUser(Uzer uzer)
    {
        entityManager.persist(uzer);
    }

    @Override
    public boolean isValid(String username, String password)
    {
        TypedQuery<Long> query = entityManager.createQuery("SELECT count(*) FROM Uzer u WHERE u.username = :username AND u.password = :password", Long.class);
        query.setParameter("username", username);
        query.setParameter("password", password);

        Long count = query.getSingleResult();
        return count == 1;
    }

    @Override
    public boolean validUsername(String username)
    {
        TypedQuery<Long> query = entityManager.createQuery("SELECT count(*) FROM Uzer u WHERE u.username = :username", Long.class);
        query.setParameter("username", username);

        Long count = query.getSingleResult();
        return count == 0;
    }


}
