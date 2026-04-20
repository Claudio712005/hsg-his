package br.com.hsg.domain.entity;

import br.com.hsg.domain.converter.IndicativoStatusConverter;
import br.com.hsg.domain.enums.IndicativoStatus;
import br.com.hsg.domain.vo.DataNascimento;
import br.com.hsg.domain.vo.Email;
import br.com.hsg.domain.vo.NomeCompleto;
import br.com.hsg.domain.vo.Telefone;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PAC", schema = "HSG")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAC")
    @SequenceGenerator(name = "SEQ_PAC", sequenceName = "SEQ_PAC", allocationSize = 1)
    @Column(name = "ID_PAC")
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "primeiroNome", column = @Column(name = "FRT_NM_PAC")),
            @AttributeOverride(name = "sobrenome", column = @Column(name = "LST_NM_PAC"))
    })
    private NomeCompleto nome;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "DS_EMAIL"))
    private Email email;

    @Column(name = "NR_CPF_HASH", nullable = false, unique = true, length = 64)
    private String cpfHash;

    @Column(name = "NR_CPF_ENC", nullable = false, length = 255)
    private String cpfEncrypted;

    @Column(name = "NR_RG_ENC", length = 255)
    private String rgEncrypted;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "DT_NASC_PAC"))
    private DataNascimento dataNascimento;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "NR_TEL"))
    private Telefone telefone;

    @Getter
    @Convert(converter = IndicativoStatusConverter.class)
    @Column(name = "ST_PAC", nullable = false, length = 1)
    private IndicativoStatus status;

    @Getter
    @Column(name = "DT_CAD_PAC", nullable = false)
    private LocalDateTime dataCadastro;

    @Getter
    @Column(name = "DT_ULT_ATU")
    private LocalDateTime dataUltimaAtualizacao;

    protected Paciente() {}

    public static Paciente criar(
            NomeCompleto nome,
            Email email,
            DataNascimento dataNascimento,
            Telefone telefone
    ){
        Paciente paciente = new Paciente();

        paciente.nome = nome;
        paciente.email = email;
        paciente.dataNascimento = dataNascimento;
        paciente.telefone = telefone;
        paciente.dataCadastro = LocalDateTime.now();
        paciente.status = IndicativoStatus.A;
        return paciente;
    }

    public boolean isAtivo() {
        return this.status == IndicativoStatus.A;
    }

    public void ativar() {
        this.status = IndicativoStatus.A;
    }

    public void inativar() {
        this.status = IndicativoStatus.I;
    }

    public void definirCpf(String cpfHash, String cpfEncrypted) {
        this.cpfHash = cpfHash;
        this.cpfEncrypted = cpfEncrypted;
    }

    public void definirRg(String rgEncrypted) {
        this.rgEncrypted = rgEncrypted;
    }

    public String getNomeCompleto() {
        return this.nome.getNomeCompleto();
    }

    public String getEmail() {
        return this.email.getValor();
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento.getValor();
    }

    public String getTelefone() {
        return this.telefone.getValor();
    }

    public void atualizarDadosPessoais(
            NomeCompleto nome
    ) {
        this.nome = nome;
        atualizarDataModificacao();
    }

    public void atualizarContato(Email email, Telefone telefone) {
        this.email = email;
        this.telefone = telefone;
        atualizarDataModificacao();
    }

    public void atualizarDataModificacao(){
        this.dataUltimaAtualizacao = LocalDateTime.now();
    }
}