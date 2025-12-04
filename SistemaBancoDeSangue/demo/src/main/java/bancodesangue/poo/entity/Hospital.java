package bancodesangue.poo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import bancodesangue.poo.util.ValidadorCNPJ;

@Entity
@Table(name = "hospital")
public class Hospital extends EntidadeBase {

    @Column(length = 200, nullable = false, name = "endereco")
    private String endereco;

    @Column(nullable = false, unique = true, name = "cnpj")
    private Long cnpj;

    @Override
    public void validarCadastro() {
        if (this.getNome() == null || this.getNome().isEmpty())
            throw new IllegalArgumentException("Nome obrigatório.");
        if (this.getEndereco() == null || this.getEndereco().isEmpty())
            throw new IllegalArgumentException("Endereço obrigatório.");
        if (!ValidadorCNPJ.validarCnpj(this.getCnpj()))
            throw new IllegalArgumentException("CNPJ inválido.");
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

}
