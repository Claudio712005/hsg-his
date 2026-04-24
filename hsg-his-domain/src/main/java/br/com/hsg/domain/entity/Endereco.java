package br.com.hsg.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ENDERECO", schema = "hsg")
public class Endereco {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENDERECO")
    @SequenceGenerator(name = "SEQ_ENDERECO", sequenceName = "SEQ_ENDERECO", allocationSize = 1)
    @Column(name = "ID_ENDERECO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAC", nullable = false, unique = true)
    private Paciente paciente;

    @Getter
    @Column(name = "DS_LOGRADOURO", length = 200)
    private String logradouro;

    @Getter
    @Column(name = "NR_ENDERECO", length = 10)
    private String numero;

    @Getter
    @Column(name = "DS_COMPLEMENTO", length = 100)
    private String complemento;

    @Getter
    @Column(name = "DS_BAIRRO", length = 100)
    private String bairro;

    @Getter
    @Column(name = "DS_CIDADE", length = 100)
    private String cidade;

    @Getter
    @Column(name = "SG_ESTADO", length = 2)
    private String estado;

    @Getter
    @Column(name = "NR_CEP", length = 8)
    private String cep;

    @Getter
    @Column(name = "DT_CAD_END")
    private LocalDateTime dataCadastro;

    @Getter
    @Column(name = "DT_ULT_ATU_END")
    private LocalDateTime dataUltimaAtualizacao;

    protected Endereco() {}

    public static Endereco criar(Paciente paciente, String logradouro, String numero,
            String complemento, String bairro, String cidade, String estado, String cep) {
        Endereco e = new Endereco();
        e.paciente    = paciente;
        e.logradouro  = logradouro;
        e.numero      = numero;
        e.complemento = complemento;
        e.bairro      = bairro;
        e.cidade      = cidade;
        e.estado      = estado;
        e.cep         = cep;
        e.dataCadastro = LocalDateTime.now();
        return e;
    }

    public void atualizar(String logradouro, String numero, String complemento,
            String bairro, String cidade, String estado, String cep) {
        this.logradouro  = logradouro;
        this.numero      = numero;
        this.complemento = complemento;
        this.bairro      = bairro;
        this.cidade      = cidade;
        this.estado      = estado;
        this.cep         = cep;
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
}
