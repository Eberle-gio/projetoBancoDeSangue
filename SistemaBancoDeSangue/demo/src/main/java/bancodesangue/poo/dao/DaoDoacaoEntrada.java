package bancodesangue.poo.dao;

import javax.persistence.EntityManager;

import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.enums.TipoSanguineo;

public class DaoDoacaoEntrada extends AbstractDao<DoacaoEntrada> {

    public DaoDoacaoEntrada(EntityManager em) {
        super(em, DoacaoEntrada.class);
    }

    // Soma a coluna 'quantidade' filtrando pelo tipo sanguíneo
    public Long somarEntradasPorTipo(TipoSanguineo tipo) {
        String jpql = "SELECT SUM(d.quantidade) FROM DoacaoEntrada d WHERE d.tipoSanguineo = :tipo";
        Long total = em.createQuery(jpql, Long.class)
                .setParameter("tipo", tipo)
                .getSingleResult();
        return total == null ? 0L : total;
    }
}