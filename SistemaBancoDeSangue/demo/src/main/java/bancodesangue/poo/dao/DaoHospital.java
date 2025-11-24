package bancodesangue.poo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import bancodesangue.poo.entity.Hospital;

public class DaoHospital extends AbstractDao<Hospital> {

    // Injeção de Dependência
    public DaoHospital(EntityManager em) {
        super(em, Hospital.class);
    }

    // Método extra para garantir unicidade do hospital
    public Hospital buscarPorCnpj(Long cnpj) {
        try {
            String jpql = "SELECT h FROM Hospital h WHERE h.cnpj = :cnpj";
            TypedQuery<Hospital> query = em.createQuery(jpql, Hospital.class);
            query.setParameter("cnpj", cnpj);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}