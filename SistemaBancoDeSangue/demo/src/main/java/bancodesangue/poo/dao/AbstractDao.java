package bancodesangue.poo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AbstractDao<T> implements Dao<T> {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenciaPU");

    protected EntityManager em;
    private Class<T> entityClass;

    public AbstractDao(Class<T> entityClass) {
        this.em = emf.createEntityManager();
        this.entityClass = entityClass;
    }

    @Override
    public void inserir(T entidade) {
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void excluir(T entidade) {
        try {
            em.getTransaction().begin();
            entidade = em.merge(entidade); // garantir que está gerenciado
            em.remove(entidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public T buscarPorId(Long id) {
        return em.find(entityClass, id);
    }

    @Override
    public List<T> buscarTodos() {
        return em.createQuery("from " + entityClass.getSimpleName(), entityClass)
                .getResultList();
    }
}
