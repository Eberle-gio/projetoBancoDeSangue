package bancodesangue.poo.service;

import java.time.LocalDate;
import java.util.List;

import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoacaoSaida;
import bancodesangue.poo.dao.DaoHospital;
import bancodesangue.poo.entity.DoacaoSaida;
import bancodesangue.poo.enums.TipoSanguineo;

public class DoacaoSaidaService {

    private DaoDoacaoSaida saidaDao;
    private DaoDoacaoEntrada entradaDao;
    private DaoHospital hospitalDao;

    // Injeção de Dependência
    public DoacaoSaidaService(DaoDoacaoSaida saidaDao, DaoDoacaoEntrada entradaDao, DaoHospital hospitalDao) {
        this.saidaDao = saidaDao;
        this.entradaDao = entradaDao;
        this.hospitalDao = hospitalDao;
    }

    public DoacaoSaida registrarSaida(DoacaoSaida saida) {
        // Data automática
        saida.setData(LocalDate.now());

        // Validações básicas
        if (saida.getQuantidadeBolsas() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (saida.getHospital() == null || saida.getHospital().getId() == null) {
            throw new IllegalArgumentException("Hospital não informado.");
        }
        if (hospitalDao.buscarPorId(saida.getHospital().getId()) == null) {
            throw new IllegalArgumentException("Hospital não encontrado.");
        }

        // 4. VERIFICAÇÃO DE ESTOQUE (Crucial!)
        validarEstoque(saida.getTipoSanguineo(), saida.getQuantidadeBolsas());

        return saidaDao.inserir(saida);
    }

    private void validarEstoque(TipoSanguineo tipo, int quantidadeSolicitada) {
        // Busca quanto entrou e quanto saiu no banco
        Long totalEntrada = entradaDao.somarEntradasPorTipo(tipo);
        Long totalSaida = saidaDao.somarSaidasPorTipo(tipo);

        long estoqueAtual = totalEntrada - totalSaida;

        if (quantidadeSolicitada > estoqueAtual) {
            throw new IllegalArgumentException("Estoque insuficiente para " + tipo +
                    ". Disponível: " + estoqueAtual + ", Solicitado: " + quantidadeSolicitada);
        }
    }

    // --- MÉTODOS PARA RELATÓRIOS ---

    public List<DoacaoSaida> buscarPorData(boolean asc) {
        return saidaDao.buscarSaidasPorData(asc);
    }

    public List<Object[]> gerarRankingHospitais() {
        return saidaDao.buscarRankingHospitais();
    }
}