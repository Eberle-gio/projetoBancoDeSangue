package bancodesangue.poo.service;

import java.time.LocalDate;
import java.util.List;

import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.entity.Doador;

public class DoacaoEntradaService {

    private DaoDoacaoEntrada daoEntrada;
    private DaoDoador daoDoador;

    public DoacaoEntradaService() {
        this.daoEntrada = new DaoDoacaoEntrada();
        this.daoDoador = new DaoDoador();
    }

    public DoacaoEntrada registrarDoacao(DoacaoEntrada entrada) {
        entrada.setData(LocalDate.now());

        if (entrada.getDoador() == null || entrada.getDoador().getId() == null) {
            throw new IllegalArgumentException("ID do Doador é obrigatório.");
        }

        Doador doador = daoDoador.buscarPorId(entrada.getDoador().getId());
        if (doador == null) {
            throw new IllegalArgumentException("Doador não encontrado.");
        }

        entrada.setDoador(doador);
        entrada.setTipoSanguineo(doador.getTipoSanguineo());

        entrada.validarMovimentacao();
        doador.validarIntervaloDoacao(LocalDate.now());

        DoacaoEntrada nova = daoEntrada.inserir(entrada);

        doador.setDataUltimaDoacao(LocalDate.now());
        daoDoador.atualizar(doador);

        return nova;
    }

    public List<Object[]> gerarRankingDoadores() {
        return daoEntrada.buscarRankingDoadores();
    }
}