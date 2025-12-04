package bancodesangue.poo.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import bancodesangue.poo.enums.Genero;
import bancodesangue.poo.enums.TipoSanguineo;
import bancodesangue.poo.util.ValidadorCPF;

@Entity
@Table(name = "doador")
public class Doador extends EntidadeBase {

    @Column(length = 14, nullable = false, unique = true, name = "cpf")
    private String cpf;

    @Column(nullable = false, unique = false, name = "idade")
    private int idade;

    @Column(nullable = false, unique = false, name = "sexo")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(nullable = false, unique = false, name = "tipoSanguineo")
    @Enumerated(EnumType.STRING)
    private TipoSanguineo tipoSanguineo;

    @Column(nullable = false, unique = false, name = "peso")
    private Double peso;

    @Column(nullable = true, unique = false, name = "dataUltimaDoacao")
    private LocalDate dataUltimaDoacao;

    @Override
    public void validarCadastro() {
        if (this.getNome() == null || this.getNome().isEmpty())
            throw new IllegalArgumentException("Nome obrigatório.");
        if (this.idade < 16 || this.idade > 69)
            throw new IllegalArgumentException("Idade inválida (16-69 anos).");
        if (this.peso < 50)
            throw new IllegalArgumentException("Peso mínimo 50kg.");
        if (!ValidadorCPF.validar(this.cpf))
            throw new IllegalArgumentException("CPF inválido.");
    }

    public void validarIntervaloDoacao(LocalDate dataAtual) {
        if (this.dataUltimaDoacao != null) {
            long dias = ChronoUnit.DAYS.between(this.dataUltimaDoacao, dataAtual);
            if (this.genero == Genero.MASCULINO && dias < 60) {
                throw new IllegalArgumentException("Homens devem aguardar 60 dias.");
            } else if (this.genero == Genero.FEMININO && dias < 90) {
                throw new IllegalArgumentException("Mulheres devem aguardar 90 dias.");
            }
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getDataUltimaDoacao() {
        return dataUltimaDoacao;
    }

    public void setDataUltimaDoacao(LocalDate dataUltimaDoacao) {
        this.dataUltimaDoacao = dataUltimaDoacao;
    }

}
