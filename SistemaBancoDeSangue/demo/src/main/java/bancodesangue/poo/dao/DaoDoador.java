package bancodesangue.poo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import bancodesangue.poo.entity.Doador;

public class DaoDoador extends AbstractDao<Doador> {

    // Injeção de Dependência: Recebe o EM do Main
    public DaoDoador(EntityManager em) {
        super(em, Doador.class);
    }

    // MELHORIA DE PERFORMANCE: Busca direta no banco
    public Doador buscarPorCpf(String cpf) {
        try {
            String jpql = "SELECT d FROM Doador d WHERE d.cpf = :cpf";
            TypedQuery<Doador> query = em.createQuery(jpql, Doador.class);
            query.setParameter("cpf", cpf);

            return query.getSingleResult();
        } catch (NoResultException e) {
            // Se não achar ninguém com esse CPF, retorna nulo (o que é bom para validações)
            return null;
        }
    }
}