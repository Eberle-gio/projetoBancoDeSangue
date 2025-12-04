package bancodesangue.poo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "doacao_saida")
@PrimaryKeyJoinColumn(name = "id")
public class DoacaoSaida extends Movimentacao {

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Column(nullable = false, name = "quantidade_bolsas")
    private int quantidadeBolsas;

    @Override
    public void validarMovimentacao() {
        if (this.hospital == null)
            throw new IllegalArgumentException("Hospital obrigatório.");
        if (this.quantidadeBolsas <= 0)
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
    }

    public void validarEstoque(long saldoDisponivel) {
        if (this.quantidadeBolsas > saldoDisponivel) {
            throw new IllegalArgumentException("Estoque insuficiente. Disponível: " + saldoDisponivel);
        }
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