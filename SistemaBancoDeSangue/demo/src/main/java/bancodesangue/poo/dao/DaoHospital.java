package bancodesangue.poo.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import bancodesangue.poo.entity.Hospital;

public class DaoHospital extends AbstractDao<Hospital> {

    public DaoHospital(EntityManager em) {
        super(em, Hospital.class);
    }

    public Hospital buscarPorCnpj(Long cnpj) {
        try {
            TypedQuery<Hospital> query = em.createQuery("SELECT h FROM Hospital h WHERE h.cnpj = :cnpj",
                    Hospital.class);
            query.setParameter("cnpj", cnpj);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}