package bancodesangue.poo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.entity.Doador;
import bancodesangue.poo.enums.Genero;

public class DoacaoEntradaService {

    private DaoDoacaoEntrada doacaoDao;
    private DaoDoador doadorDao;
    private final int quantidadeBolsa = 1;

    public DoacaoEntradaService() {
        this.doacaoDao = new DaoDoacaoEntrada();
        this.doadorDao = new DaoDoador();
    }

    public DoacaoEntrada registrarDoacao(DoacaoEntrada doacao) {
        validarDoacao(doacao);

        Doador doador = doacao.getDoador();
        doador.setDataUltimaDoacao(doacao.getDataEntrada());
        doadorDao.atualizar(doador);
        return doacaoDao.inserir(doacao);
    }

    private void validarDoacao(DoacaoEntrada doacao) {
        Doador doador = doacao.getDoador();

        if (doador == null) {
            throw new IllegalArgumentException("Doador não informado.");
        }

        if (doador.getDataUltimaDoacao() != null) {
            long diasUltimaDoacao = ChronoUnit.DAYS.between(doador.getDataUltimaDoacao(), doacao.getDataEntrada());

            if (doador.getGenero() == Genero.FEMININO && diasUltimaDoacao < 90) {
                throw new IllegalArgumentException("Doadora não pode doar sangue antes de 90 dias da última doação.");
            }

            if (doador.getGenero() == Genero.MASCULINO && diasUltimaDoacao < 60) {
                throw new IllegalArgumentException("Doador não pode doar sangue antes de 60 dias da última doação.");
            }
        }

        if (doador.getPeso() < 50) {
            throw new IllegalArgumentException("Doador não pode doar sangue com peso inferior a 50kg.");
        }

        if (doador.getIdade() < 16 || doador.getIdade() > 69) {
            throw new IllegalArgumentException("Doador deve ter entre 18 e 69 anos para doar sangue.");
        }

        if (doador.getTipoSanguineo() == null) {
            throw new IllegalArgumentException("Tipo sanguíneo do doador não informado.");
        }

        if (doacao.getDataEntrada() == null) {
            doacao.setDataEntrada(LocalDate.now());
        }

    }
}