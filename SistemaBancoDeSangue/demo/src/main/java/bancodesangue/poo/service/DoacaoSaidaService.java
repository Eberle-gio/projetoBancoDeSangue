package bancodesangue.poo.service;

import java.time.LocalDate;

import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.dao.DaoDoacaoSaida;
import bancodesangue.poo.dao.DaoHospital;
import bancodesangue.poo.entity.DoacaoSaida;
import bancodesangue.poo.enums.TipoSanguineo;

public class DoacaoSaidaService {

    private DaoDoacaoSaida saidaDao;
    private DaoDoacaoEntrada entradaDao;
    private DaoHospital hospitalDao;

    public DoacaoSaidaService(DaoDoacaoSaida saidaDao, DaoDoacaoEntrada entradaDao, DaoHospital hospitalDao) {
        this.saidaDao = saidaDao;
        this.entradaDao = entradaDao;
        this.hospitalDao = hospitalDao;
    }

    public DoacaoSaida registrarSaida(DoacaoSaida saida) {
        saida.setData(LocalDate.now());

        if (saida.getQuantidadeBolsas() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
        if (saida.getHospital() == null || hospitalDao.buscarPorId(saida.getHospital().getId()) == null) {
            throw new IllegalArgumentException("Hospital inválido ou não encontrado.");
        }

        // Validação de Estoque
        verificarEstoque(saida.getTipoSanguineo(), saida.getQuantidadeBolsas());

        return saidaDao.inserir(saida);
    }

    private void verificarEstoque(TipoSanguineo tipo, int qtdSolicitada) {
        Long totalEntradas = entradaDao.somarEntradasPorTipo(tipo);
        Long totalSaidas = saidaDao.somarSaidasPorTipo(tipo);

        long saldoAtual = totalEntradas - totalSaidas;

        if (qtdSolicitada > saldoAtual) {
            throw new IllegalArgumentException("Estoque insuficiente para " + tipo +
                    ". Disponível: " + saldoAtual);
        }
    }
}