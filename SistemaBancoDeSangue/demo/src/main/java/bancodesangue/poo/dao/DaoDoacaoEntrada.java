package bancodesangue.poo.dao;

import java.time.LocalDate;

import bancodesangue.poo.entity.DoacaoEntrada;

public class DaoDoacaoEntrada extends AbstractDao<DoacaoEntrada> {
    private LocalDate dataEntrada;

    public DaoDoacaoEntrada() {
        super(DoacaoEntrada.class);
    }

}
