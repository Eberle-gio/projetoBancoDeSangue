package bancodesangue.poo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "doacao_entrada")
@PrimaryKeyJoinColumn(name = "id") // Opcional, mas bom para explicitar que o ID é FK da Movimentacao
public class DoacaoEntrada extends Movimentacao {

    @ManyToOne
    @JoinColumn(name = "doador_id", nullable = false)
    private Doador doador;

    // Adicionei para facilitar o cálculo de estoque (soma de quantidades)
    @Column(nullable = false)
    private int quantidade = 1;

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}