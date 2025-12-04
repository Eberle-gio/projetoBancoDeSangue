package bancodesangue.poo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "doacao_descarte")
@PrimaryKeyJoinColumn(name = "id")
public class Descarte extends Movimentacao {

    @Column(nullable = false)
    private String motivoDescarte;

    @Column(nullable = false)
    private String responsavelDescarte;

    @Override
    public void validarMovimentacao() {
        if (this.getTipoSanguineo() == null) {
            throw new IllegalArgumentException("Tipo Sanguíneo é obrigatório para o descarte.");
        }
        if (this.motivoDescarte == null || this.motivoDescarte.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo do descarte é obrigatório.");
        }
        if (this.responsavelDescarte == null || this.responsavelDescarte.trim().isEmpty()) {
            throw new IllegalArgumentException("Responsável pelo descarte é obrigatório.");
        }
    }

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