package bancodesangue.poo.service;

import java.util.List;

import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.entity.Doador;
import bancodesangue.poo.util.ValidadorCPF;

public class DoadorService {

    private DaoDoador doadorDao;

    public DoadorService(DaoDoador doadorDao) {
        this.doadorDao = doadorDao;
    }

    public Doador cadastrarDoador(Doador doador) {
        validarDoador(doador);
        return doadorDao.inserir(doador);
    }

    private void validarDoador(Doador doador) {
        if (doador.getIdade() < 16 || doador.getIdade() > 69)
            throw new IllegalArgumentException("Idade inválida (16 a 69 anos).");

        if (doador.getPeso() < 50)
            throw new IllegalArgumentException("Peso mínimo é 50kg.");

        if (!ValidadorCPF.validar(doador.getCpf())) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        // Validação de duplicidade otimizada
        Doador existente = doadorDao.buscarPorCpf(doador.getCpf());
        if (existente != null && (doador.getId() == null || !existente.getId().equals(doador.getId()))) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
    }

    public List<Doador> buscarTodos() {
        return doadorDao.buscarTodos();
    }

    public Doador buscarPorNome(String nome) {
        return doadorDao.buscarPorNome(nome);
    }
}