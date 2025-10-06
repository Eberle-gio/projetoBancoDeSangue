package bancodesangue.poo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doador")
public class Doador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, name = "nome")
    private String nome;

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

    @Column(length = 15, nullable = false, unique = false, name = "telefone")
    private String telefone;

    @Column(nullable = false, unique = false, name = "peso")
    private Double peso;

    @Column(nullable = true, unique = false, name = "dataUltimaDoacao")
    private LocalDate dataUltimaDoacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
