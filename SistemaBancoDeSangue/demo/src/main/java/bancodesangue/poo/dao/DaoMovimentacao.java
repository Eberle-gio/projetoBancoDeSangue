package bancodesangue.poo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import bancodesangue.poo.entity.Movimentacao;

public class DaoMovimentacao extends AbstractDao<Movimentacao> {

    public DaoMovimentacao(EntityManager em) {
        super(em, Movimentacao.class);
    }

    public List<Movimentacao> buscarTodosOrdenadoPorData(boolean ascendente) {
        String ordem = ascendente ? "ASC" : "DESC";
        return em.createQuery("SELECT m FROM Movimentacao m ORDER BY m.data " + ordem, Movimentacao.class)
                .getResultList();
    }
}