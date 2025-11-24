package bancodesangue.poo.dao;

import javax.persistence.EntityManager;

import bancodesangue.poo.entity.DoacaoSaida;
import bancodesangue.poo.enums.TipoSanguineo;

public class DaoDoacaoSaida extends AbstractDao<DoacaoSaida> {

    public DaoDoacaoSaida(EntityManager em) {
        super(em, DoacaoSaida.class);
    }

    public Long somarSaidasPorTipo(TipoSanguineo tipo) {
        String jpql = "SELECT SUM(d.quantidadeBolsas) FROM DoacaoSaida d WHERE d.tipoSanguineo = :tipo";
        Long total = em.createQuery(jpql, Long.class)
                .setParameter("tipo", tipo)
                .getSingleResult();
        return total == null ? 0L : total;
    }
}