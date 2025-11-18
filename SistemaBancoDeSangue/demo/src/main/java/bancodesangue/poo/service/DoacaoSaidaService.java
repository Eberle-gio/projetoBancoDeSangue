package bancodesangue.poo.service;

import bancodesangue.poo.dao.DaoDoacaoSaida;
import bancodesangue.poo.dao.DaoDoador;

public class DoacaoSaidaService {
    private DaoDoacaoSaida doacaoDao;
    private DaoDoador doadorDao;

    public DoacaoSaidaService() {
        this.doacaoDao = new DaoDoacaoSaida();
        this.doadorDao = new DaoDoador();
    }

}
