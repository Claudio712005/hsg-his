package br.com.hsg.domain.entity;

import br.com.hsg.domain.enums.IndicativoStatus;
import br.com.hsg.domain.enums.Sexo;
import br.com.hsg.domain.vo.DataNascimento;
import br.com.hsg.domain.vo.Email;
import br.com.hsg.domain.vo.NomeCompleto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "TB_PAC", schema = "HSG")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAC")
    @SequenceGenerator(name = "SEQ_PAC", sequenceName = "SEQ_PAC", allocationSize = 1)
    @Column(name = "ID_PAC")
    private Long id;

    @Getter
    @Embedded
    private NomeCompleto nome;

    @Getter
    @Embedded
    private Email email;

    @Column(name = "NR_CPF_HASH", nullable = false, unique = true)
    private String cpfHash;

    @Column(name = "NR_CPF_ENC", nullable = false)
    private String cpfEncrypted;

    @Column(name = "NR_RG_ENC", nullable = false)
    private String rgEncrypted;

    @Getter
    @Embedded
    private DataNascimento dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "ST_PAC", nullable = false)
    private IndicativoStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "SX_PAC", nullable = false)
    private Sexo sexo;

    protected Paciente() {}

    public Paciente(NomeCompleto nome, Email email) {
        this.nome = nome;
        this.email = email;
        this.status = IndicativoStatus.A;
    }

    public void ativar() {
        this.status = IndicativoStatus.A;
    }

    public void inativar() {
        this.status = IndicativoStatus.I;
    }
}