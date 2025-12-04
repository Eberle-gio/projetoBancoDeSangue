package bancodesangue.poo.service;

import java.util.List;

import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.entity.Doador;

public class DoadorService {

    private DaoDoador dao;

    public DoadorService() {
        this.dao = new DaoDoador();
    }

    public Doador cadastrarDoador(Doador doador) {

        doador.validarCadastro();

        Doador existente = dao.buscarPorCpf(doador.getCpf());
        if (existente != null && (doador.getId() == null || !existente.getId().equals(doador.getId()))) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema.");
        }

        return dao.inserir(doador);
    }

    public Doador atualizarDoador(Doador doador) {
        doador.validarCadastro();
        return dao.atualizar(doador);
    }

    public List<Doador> buscarTodos() {
        return dao.buscarTodos();
    }

    public Doador buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }

    public Doador buscarPorNome(String nome) {
        return dao.buscarPorNome(nome);
    }
}