package bancodesangue.poo.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doacao_entrada")
public class DoacaoEntrada extends Movimentacao {

    @ManyToOne
    @JoinColumn(name = "doador_id")
    private Doador doador;

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }
}
