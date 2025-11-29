package bancodesangue.poo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.enums.TipoSanguineo;

public class DaoDoacaoEntrada extends AbstractDao<DoacaoEntrada> {

    public DaoDoacaoEntrada(EntityManager em) {
        super(em, DoacaoEntrada.class);
    }

    public Long somarEntradasPorTipo(TipoSanguineo tipo) {
        Long total = em
                .createQuery("SELECT SUM(d.quantidade) FROM DoacaoEntrada d WHERE d.tipoSanguineo = :tipo", Long.class)
                .setParameter("tipo", tipo)
                .getSingleResult();

        return total == null ? 0L : total;
    }

    public List<Object[]> buscarRankingDoadores() {
        return em.createQuery(
                "SELECT e.doador, SUM(e.quantidade) FROM DoacaoEntrada e GROUP BY e.doador ORDER BY SUM(e.quantidade) DESC",
                Object[].class)
                .getResultList();
    }
}