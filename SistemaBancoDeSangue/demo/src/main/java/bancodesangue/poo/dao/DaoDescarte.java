package bancodesangue.poo.dao;

import javax.persistence.EntityManager;

import bancodesangue.poo.entity.Descarte;

public class DaoDescarte extends AbstractDao<Descarte> {

    public DaoDescarte(EntityManager em) {
        super(em, Descarte.class);
    }
}