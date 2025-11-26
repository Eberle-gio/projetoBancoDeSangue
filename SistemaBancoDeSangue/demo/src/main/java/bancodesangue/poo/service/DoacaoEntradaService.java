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

    public DoacaoEntradaService(DaoDoacaoEntrada doacaoDao, DaoDoador doadorDao) {
        this.doacaoDao = doacaoDao;
        this.doadorDao = doadorDao;
    }

    public DoacaoEntrada registrarDoacao(DoacaoEntrada doacao) {
        // Data automática
        doacao.setData(LocalDate.now());

        if (doacao.getDoador() == null || doacao.getDoador().getId() == null) {
            throw new IllegalArgumentException("Doador é obrigatório.");
        }
        // Busca de Doador
        Doador doador = doadorDao.buscarPorId(doacao.getDoador().getId());
        if (doador == null) {
            throw new IllegalArgumentException("Doador não encontrado.");
        }

        doacao.setTipoSanguineo(doador.getTipoSanguineo());

        // Intervalo entre doações
        validarIntervalo(doador);
        DoacaoEntrada novaDoacao = doacaoDao.inserir(doacao);

        // Atualiza a ultima doação do doador
        doador.setDataUltimaDoacao(LocalDate.now());
        doadorDao.atualizar(doador);

        return novaDoacao;
    }

    private void validarIntervalo(Doador doador) {
        if (doador.getDataUltimaDoacao() != null) {
            long dias = ChronoUnit.DAYS.between(doador.getDataUltimaDoacao(), LocalDate.now());

            if (doador.getGenero() == Genero.MASCULINO && dias < 60) {
                throw new IllegalArgumentException("Homens devem aguardar 60 dias. Faltam: " + (60 - dias));
            } else if (doador.getGenero() == Genero.FEMININO && dias < 90) {
                throw new IllegalArgumentException("Mulheres devem aguardar 90 dias. Faltam: " + (90 - dias));
            }
        }
    }
}