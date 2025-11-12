package bancodesangue.poo.service;

import java.time.LocalDate;

import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.entity.Doador;

public class DoacaoEntradaService {

    private DaoDoacaoEntrada doacaoDao;
    private DaoDoador doadorDao;

    public DoacaoEntradaService() {
        this.doacaoDao = new DaoDoacaoEntrada();
        this.doadorDao = new DaoDoador();
    }

    public DoacaoEntrada registrarDoacao(DoacaoEntrada doacao) {
        validarDoacao(doacao);
        return doacaoDao.inserir(doacao);
    }

    private void validarDoacao(DoacaoEntrada doacao) {
        if (doacao.getData() == null || doacao.getData().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de doação inválida.");
        }

        if (doacao.getDoador() == null) {
            throw new IllegalArgumentException("A doação deve estar associada a um doador.");
        }

        Doador doador = doadorDao.buscarPorId(doacao.getDoador().getId());
        if (doador == null) {
            throw new IllegalArgumentException("Doador não encontrado.");
        }

    }

}
