package bancodesangue.poo.service;

import bancodesangue.poo.dao.DaoDoacaoEntrada;
import bancodesangue.poo.entity.DoacaoEntrada;

public class DoacaoEntradaService {

    private DaoDoacaoEntrada doacaoEntradaDao;

    public DoacaoEntradaService() {
        this.doacaoEntradaDao = new DaoDoacaoEntrada();
    }

    public DoacaoEntrada cadastrarDoacao(DoacaoEntrada doacaoEntrada) {
        return doacaoEntradaDao.inserir(doacaoEntrada);
    }

}
