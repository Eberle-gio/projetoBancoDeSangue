package bancodesangue.poo.service;

import java.util.List;

import bancodesangue.poo.dao.DaoMovimentacao;
import bancodesangue.poo.entity.Movimentacao;

public class MovimentacaoService {

    private DaoMovimentacao dao;

    public MovimentacaoService() {
        this.dao = new DaoMovimentacao();
    }

    public List<Movimentacao> buscarHistoricoCompleto() {
        return dao.buscarTodos();
    }

    public List<Movimentacao> buscarPorData(boolean ascendente) {
        return dao.buscarTodosOrdenadoPorData(ascendente);
    }
}