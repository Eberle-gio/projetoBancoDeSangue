package bancodesangue.poo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "doacao_descarte")
@PrimaryKeyJoinColumn(name = "id")
public class DoacaoDescarte extends Movimentacao {

    @Column(nullable = false)
    private String motivoDescarte;

    @Column(nullable = false)
    private String responsavelDescarte;

    public String getMotivoDescarte() {
        return motivoDescarte;
    }

    public void setMotivoDescarte(String motivoDescarte) {
        this.motivoDescarte = motivoDescarte;
    }

    public String getResponsavelDescarte() {
        return responsavelDescarte;
    }

    public void setResponsavelDescarte(String responsavelDescarte) {
        this.responsavelDescarte = responsavelDescarte;
    }
}