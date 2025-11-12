package bancodesangue.poo.service;

import java.util.List;

import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.entity.Doador;
import bancodesangue.poo.util.ValidadorCPF;

public class DoadorService {

    private DaoDoador doadorDao;

    public DoadorService() {
        this.doadorDao = new DaoDoador();
    }

    public Doador cadastrarDoador(Doador doador) {
        validarDoador(doador);
        return doadorDao.inserir(doador);
    }

    private void validarDoador(Doador doador) {
        if (doador.getIdade() < 16 || doador.getIdade() > 69)
            throw new IllegalArgumentException("Idade fora da faixa permitida.");
        if (doador.getPeso() < 50)
            throw new IllegalArgumentException("Peso mínimo para doar é 50 kg.");

        for (Doador d : doadorDao.buscarTodos()) {
            if (doador.getId() == null || !d.getId().equals(doador.getId())) {
                if (d.getCpf().equals(doador.getCpf())) {
                    throw new IllegalArgumentException("CPF já cadastrado.");
                }
            }
        }

        if (!ValidadorCPF.validar(doador.getCpf())) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    public Doador buscarPorNome(String nome) {
        return doadorDao.buscarPorNome(nome);
    }

    public Doador atualizarDoador(Doador doador) {
        return doadorDao.atualizar(doador);
    }

    public List<Doador> buscarTodos() {
        return doadorDao.buscarTodos();
    }

    public Doador excluirDoador(Doador doador) {
        return doadorDao.excluir(doador);
    }
}
