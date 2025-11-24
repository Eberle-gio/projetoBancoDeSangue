package bancodesangue.poo.dao;

import javax.persistence.EntityManager;

import bancodesangue.poo.entity.DoacaoDescarte;

public class DaoDescarte extends AbstractDao<DoacaoDescarte> {

    // Injeção de dependência do EntityManager
    public DaoDescarte(EntityManager em) {
        super(em, DoacaoDescarte.class);
    }
}