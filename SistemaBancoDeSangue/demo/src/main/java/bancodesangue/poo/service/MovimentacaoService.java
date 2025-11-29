package bancodesangue.poo.service;

import java.util.List;

import bancodesangue.poo.dao.DaoMovimentacao;
import bancodesangue.poo.entity.Movimentacao;

public class MovimentacaoService {

    private DaoMovimentacao dao;

    public MovimentacaoService(DaoMovimentacao dao) {
        this.dao = dao;
    }

    public List<Movimentacao> buscarHistoricoCompleto() {
        return dao.buscarTodos();
    }

    public List<Movimentacao> buscarPorData(boolean asc) {
        return dao.buscarTodosOrdenadoPorData(asc);
    }
}