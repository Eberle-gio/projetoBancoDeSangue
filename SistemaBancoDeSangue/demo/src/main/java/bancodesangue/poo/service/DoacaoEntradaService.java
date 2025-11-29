package bancodesangue.poo.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoador;
import bancodesangue.poo.entity.DoacaoEntrada;
import bancodesangue.poo.entity.Doador;
import bancodesangue.poo.enums.Genero;

public class DoacaoEntradaService {

    private DaoDoacaoEntrada doacaoDao;
    private DaoDoador doadorDao;

    // Injeção de Dependência
    public DoacaoEntradaService(DaoDoacaoEntrada doacaoDao, DaoDoador doadorDao) {
        this.doacaoDao = doacaoDao;
        this.doadorDao = doadorDao;
    }

    public DoacaoEntrada registrarDoacao(DoacaoEntrada doacao) {
        // 1. DATA AUTOMÁTICA
        doacao.setData(LocalDate.now());

        // Valida se o doador foi informado
        if (doacao.getDoador() == null || doacao.getDoador().getId() == null) {
            throw new IllegalArgumentException("Doador é obrigatório.");
        }

        // Busca o doador atualizado do banco para garantir os dados
        Doador doador = doadorDao.buscarPorId(doacao.getDoador().getId());
        if (doador == null) {
            throw new IllegalArgumentException("Doador não encontrado no banco de dados.");
        }

        // Garante que o tipo sanguíneo da doação é o mesmo do doador
        doacao.setTipoSanguineo(doador.getTipoSanguineo());

        // 2. REGRA DO INTERVALO ENTRE DOAÇÕES
        validarIntervaloDoacao(doador);

        // Se passou em tudo, salva a doação
        DoacaoEntrada novaDoacao = doacaoDao.inserir(doacao);

        // 3. ATUALIZA O DOADOR (Última doação = Hoje)
        doador.setDataUltimaDoacao(LocalDate.now());
        doadorDao.atualizar(doador);

        return novaDoacao;
    }

    private void validarIntervaloDoacao(Doador doador) {
        LocalDate ultimaDoacao = doador.getDataUltimaDoacao();

        if (ultimaDoacao != null) {
            long diasPassados = ChronoUnit.DAYS.between(ultimaDoacao, LocalDate.now());

            if (doador.getGenero() == Genero.MASCULINO && diasPassados < 60) {
                throw new IllegalArgumentException(
                        "Homens devem aguardar 60 dias. Faltam: " + (60 - diasPassados) + " dias.");
            } else if (doador.getGenero() == Genero.FEMININO && diasPassados < 90) {
                throw new IllegalArgumentException(
                        "Mulheres devem aguardar 90 dias. Faltam: " + (90 - diasPassados) + " dias.");
            }
        }
    }

    // --- MÉTODOS PARA RELATÓRIOS ---

    public List<Object[]> gerarRankingDoadores() {
        return doacaoDao.buscarRankingDoadores();
    }
}