package bancodesangue.poo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import bancodesangue.poo.entity.Doador;

public class DaoDoador extends AbstractDao<Doador> {

    public DaoDoador(EntityManager em) {
        super(em, Doador.class);
    }

    public Doador buscarPorCpf(String cpf) {
        try {
            // Query passada diretamente no método
            TypedQuery<Doador> query = em.createQuery("SELECT d FROM Doador d WHERE d.cpf = :cpf", Doador.class);
            query.setParameter("cpf", cpf);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}