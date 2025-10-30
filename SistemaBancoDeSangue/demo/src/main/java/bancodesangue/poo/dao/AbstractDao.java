package bancodesangue.poo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AbstractDao<T> implements DaoGenerico<T> {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenciaPU");

    protected EntityManager em;
    private Class<T> entityClass;

    public AbstractDao(Class<T> entityClass) {
        this.em = emf.createEntityManager();
        this.entityClass = entityClass;
    }

    @Override
    public T inserir(T entidade) {
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        return entidade;
    }

    @Override
    public T excluir(T entidade) {
        try {
            em.getTransaction().begin();
            entidade = em.merge(entidade);
            em.remove(entidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        return entidade;
    }

    @Override
    public T atualizar(T entidade) {
        try {
            em.getTransaction().begin();
            entidade = em.merge(entidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
        return entidade;
    }

    @Override
    public T buscarPorId(Long id) {
        return em.find(entityClass, id);
    }

    @Override
    public T buscarPorNome(String nome) {
        List<T> resultados = em.createQuery("from " + entityClass.getSimpleName() + " where nome = :nome", entityClass)
                .setParameter("nome", nome)
                .getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public List<T> buscarTodos() {
        return em.createQuery("from " + entityClass.getSimpleName(), entityClass)
                .getResultList();
    }
}
