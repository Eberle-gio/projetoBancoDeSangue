package bancodesangue.poo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

// Importante: Importar o JPAUtil que criamos
import bancodesangue.poo.util.JPAUtil;

public abstract class AbstractDao<T> implements DaoGenerico<T> {

    protected EntityManager em;
    private Class<T> entityClass;

    // CONSTRUTOR MUDOU:
    // Não recebe mais o EntityManager. Ele cria o seu próprio.
    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
        // Pega uma conexão nova da fábrica
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public T inserir(T entidade) {
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
            return entidade;
        } catch (Exception e) {
            // Rollback seguro
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao inserir registro: " + e.getMessage(), e);
        }
    }

    @Override
    public T atualizar(T entidade) {
        try {
            em.getTransaction().begin();
            entidade = em.merge(entidade);
            em.getTransaction().commit();
            return entidade;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar registro: " + e.getMessage(), e);
        }
    }

    @Override
    public T excluir(T entidade) {
        try {
            em.getTransaction().begin();
            entidade = em.merge(entidade);
            em.remove(entidade);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Aqui é melhor lançar RuntimeException para não obrigar quem chama a tratar
            throw new RuntimeException("Erro ao excluir registro: " + e.getMessage(), e);
        }
        return entidade;
    }

    @Override
    public T buscarPorId(Long id) {
        return em.find(entityClass, id);
    }

    @Override
    public T buscarPorNome(String nome) {
        try {
            // Melhor usar TypedQuery para evitar warnings e erros
            String jpql = "FROM " + entityClass.getSimpleName() + " WHERE nome = :nome";
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            query.setParameter("nome", nome);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<T> buscarTodos() {
        return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                .getResultList();
    }

    // Método útil para fechar a conexão quando o DAO não for mais usado
    public void fechar() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}