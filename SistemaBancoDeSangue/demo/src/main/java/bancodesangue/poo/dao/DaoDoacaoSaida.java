package bancodesangue.poo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import bancodesangue.poo.entity.DoacaoSaida;
import bancodesangue.poo.enums.TipoSanguineo;

public class DaoDoacaoSaida extends AbstractDao<DoacaoSaida> {

    public DaoDoacaoSaida(EntityManager em) {
        super(DoacaoSaida.class);
    }

    public Long somarSaidasPorTipo(TipoSanguineo tipo) {
        Long total = em
                .createQuery("SELECT SUM(d.quantidadeBolsas) FROM DoacaoSaida d WHERE d.tipoSanguineo = :tipo",
                        Long.class)
                .setParameter("tipo", tipo)
                .getSingleResult();

        return total == null ? 0L : total;
    }

    public List<DoacaoSaida> buscarSaidasPorData(boolean ascendente) {
        String ordem = ascendente ? "ASC" : "DESC";
        return em.createQuery("SELECT s FROM DoacaoSaida s ORDER BY s.data " + ordem, DoacaoSaida.class)
                .getResultList();
    }

    public List<Object[]> buscarRankingHospitais() {
        return em.createQuery(
                "SELECT s.hospital, SUM(s.quantidadeBolsas) FROM DoacaoSaida s GROUP BY s.hospital ORDER BY SUM(s.quantidadeBolsas) DESC",
                Object[].class)
                .getResultList();
    }
}