package bancodesangue.poo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doacao_saida")
public class DoacaoSaida extends Movimentacao {

    @ManyToOne()
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(nullable = false, name = "quantidade_bolsas")
    private int quantidadeBolsas;

    public DoacaoSaida() {
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public int getQuantidadeBolsas() {
        return quantidadeBolsas;
    }

    public void setQuantidadeBolsas(int quantidadeBolsas) {
        this.quantidadeBolsas = quantidadeBolsas;
    }

}
