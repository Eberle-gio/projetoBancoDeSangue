package bancodesangue.poo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
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

    @Column(length = 100, nullable = false, unique = true, name = "nome_Doador")
    private String nome;

    @Column(length = 14, nullable = false, unique = true, name = "cpf_Doador")
    private String cpf;

    @Column(nullable = false, unique = false, name = "idade_Doador")
    private int idade;

    @Column(nullable = false, unique = false, name = "genero_Doador")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(nullable = false, unique = false, name = "tipoSanguineo_Doador")
    @Enumerated(EnumType.STRING)
    private TipoSanguineo tipoSanguineo;

    @Column(length = 15, nullable = false, unique = false, name = "telefone_Doador")
    private String telefone;

    @Column(nullable = false, unique = false, name = "peso_Doador")
    private Double peso;

    @Column(nullable = true, unique = false, name = "dataUltimaDoacao_Doador")
    private LocalDate dataUltimaDoacao;

}
