package bancodesangue.poo.service;

import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.entity.Doador;

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

        // Verificar CPF único
        for (Doador d : doadorDao.buscarTodos()) {
            if (d.getCpf().equals(doador.getCpf()))
                throw new IllegalArgumentException("CPF já cadastrado.");
        }
    }
}
