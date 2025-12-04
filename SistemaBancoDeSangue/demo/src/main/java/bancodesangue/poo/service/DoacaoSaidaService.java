package bancodesangue.poo.service;

import java.time.LocalDate;
import java.util.List;

import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoacaoSaida;
import bancodesangue.poo.dao.DaoHospital;
import bancodesangue.poo.entity.DoacaoSaida;
import bancodesangue.poo.enums.TipoSanguineo;

public class DoacaoSaidaService {

    private DaoDoacaoSaida daoSaida;
    private DaoDoacaoEntrada daoEntrada;
    private DaoHospital daoHospital;

    public DoacaoSaidaService() {
        this.daoSaida = new DaoDoacaoSaida();
        this.daoEntrada = new DaoDoacaoEntrada();
        this.daoHospital = new DaoHospital();
    }

    public DoacaoSaida registrarSaida(DoacaoSaida saida) {
        saida.setData(LocalDate.now());

        if (saida.getHospital() == null || saida.getHospital().getId() == null) {
            throw new IllegalArgumentException("ID do Hospital é obrigatório.");
        }
        if (daoHospital.buscarPorId(saida.getHospital().getId()) == null) {
            throw new IllegalArgumentException("Hospital não encontrado.");
        }

        saida.validarMovimentacao();

        long saldo = consultarEstoqueAtual(saida.getTipoSanguineo());
        saida.validarEstoque(saldo);

        return daoSaida.inserir(saida);
    }

    public long consultarEstoqueAtual(TipoSanguineo tipo) {
        Long entradas = daoEntrada.somarEntradasPorTipo(tipo);
        Long saidas = daoSaida.somarSaidasPorTipo(tipo);
        return entradas - saidas;
    }

    public List<DoacaoSaida> buscarPorData(boolean ascendente) {
        return daoSaida.buscarSaidasPorData(ascendente);
    }

    public List<Object[]> gerarRankingHospitais() {
        return daoSaida.buscarRankingHospitais();
    }
}