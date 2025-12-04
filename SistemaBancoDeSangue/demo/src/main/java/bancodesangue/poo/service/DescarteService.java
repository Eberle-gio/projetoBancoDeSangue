package bancodesangue.poo.service;

import java.time.LocalDate;

import bancodesangue.poo.dao.DaoDescarte;
import bancodesangue.poo.entity.Descarte;

public class DescarteService {

    private DaoDescarte dao;

    public DescarteService() {
        this.dao = new DaoDescarte();
    }

    public Descarte registrarDescarte(Descarte descarte) {
        descarte.setData(LocalDate.now());
        descarte.validarMovimentacao();
        return dao.inserir(descarte);
    }
}