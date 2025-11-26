package bancodesangue.poo.service;

import java.time.LocalDate;

import bancodesangue.poo.dao.DaoDescarte;
import bancodesangue.poo.entity.DoacaoDescarte;

public class DescarteService {

    private DaoDescarte descarteDao;

    public DescarteService(DaoDescarte descarteDao) {
        this.descarteDao = descarteDao;
    }

    public DoacaoDescarte registrarDescarte(DoacaoDescarte descarte) {
        descarte.setData(LocalDate.now());

        if (descarte.getMotivoDescarte() == null || descarte.getMotivoDescarte().isEmpty()) {
            throw new IllegalArgumentException("Motivo do descarte é obrigatório.");
        }
        if (descarte.getResponsavelDescarte() == null || descarte.getResponsavelDescarte().isEmpty()) {
            throw new IllegalArgumentException("Responsável pelo descarte é obrigatório.");
        }
        if (descarte.getTipoSanguineo() == null) {
            throw new IllegalArgumentException("Tipo sanguíneo é obrigatório.");
        }

        return descarteDao.inserir(descarte);
    }
}