package bancodesangue.poo.dao;

import javax.persistence.EntityManager;

import bancodesangue.poo.entity.DoacaoDescarte;

public class DaoDescarte extends AbstractDao<DoacaoDescarte> {

    public DaoDescarte(EntityManager em) {
        super(em, DoacaoDescarte.class);
    }
}