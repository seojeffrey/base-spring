package jeff.baseproject.repository;

import jeff.baseproject.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import java.util.*;


public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {

        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> users = em.createQuery("SELECT m FROM User m WHERE m.name=:name", User.class)
                .setParameter("name", name).getResultList();
        return users.stream().findAny();
    }

    @Override
    public List<User> findAll() {

        return em.createQuery("SELECT m FROM User m", User.class)
                .getResultList();
    }

    public void clearStore()
    {

    }
}
