package br.com.hsg.domain.entity;

/*
 * Decisão arquitetural: Opção B — tabela única com discriminador tipo_profissional.
 *
 * Justificativa: os campos comuns (nome, email, cpf, status, token, admin, datas de controle)
 * superam numericamente os específicos por tipo (3 campos para médico, 3 para enfermeiro).
 * Uma tabela única simplifica queries de listagem, o service e a UI. Os campos específicos
 * ficam nullable e são validados no service de acordo com o tipo selecionado.
 */

import br.com.hsg.domain.enums.CategoriaCoren;
import br.com.hsg.domain.enums.StatusPreCadastro;
import br.com.hsg.domain.enums.TipoProfissional;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "TB_PRE_CAD_PROF", schema = "hsg")
public class PreCadastroProfissional {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRE_CAD_PROF")
    @SequenceGenerator(name = "SEQ_PRE_CAD_PROF", sequenceName = "SEQ_PRE_CAD_PROF", allocationSize = 1)
    @Column(name = "ID_PRE_CAD_PROF")
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_PROFISSIONAL", nullable = false, length = 12)
    private TipoProfissional tipoProfissional;

    @Getter
    @Column(name = "NM_PROFISSIONAL", nullable = false, length = 255)
    private String nome;

    @Getter
    @Column(name = "DS_EMAIL_PROF", nullable = false, length = 255)
    private String email;

    @Getter
    @Column(name = "NR_CPF_PROF", nullable = false, length = 14)
    private String cpf;

    /* --- campos específicos de Médico (nullable) --- */

    @Getter
    @Column(name = "NR_CRM_PROF", length = 20)
    private String crm;

    @Getter
    @Column(name = "UF_CRM_PROF", length = 2)
    private String ufCrm;

    @Getter
    @Column(name = "DS_ESPECIALIDADE_PROF", length = 100)
    private String especialidade;

    /* --- campos específicos de Enfermeiro (nullable) --- */

    @Getter
    @Column(name = "NR_COREN_PROF", length = 20)
    private String coren;

    @Getter
    @Column(name = "UF_COREN_PROF", length = 2)
    private String ufCoren;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "CAT_COREN_PROF", length = 3)
    private CategoriaCoren categoriaCoren;

    /* --- campos de controle do pré-cadastro --- */

    @Getter
    @Column(name = "ID_ADM_CRIADOR", nullable = false)
    private Long idAdminCriador;

    @Getter
    @Column(name = "DT_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "ST_PRE_CAD", nullable = false, length = 10)
    private StatusPreCadastro status;

    @Getter
    @Column(name = "TOKEN_CONVITE", nullable = false, unique = true, length = 36)
    private String tokenConvite;

    @Getter
    @Column(name = "FL_EMAIL_ENVIADO", nullable = false)
    private boolean emailEnviado;

    @Getter
    @Column(name = "DT_ENVIO_EMAIL")
    private LocalDateTime dataEnvioEmail;

    protected PreCadastroProfissional() {}

    public static PreCadastroProfissional criarParaMedico(
            String nome,
            String email,
            String cpf,
            String crm,
            String ufCrm,
            String especialidade,
            Long idAdminCriador) {

        PreCadastroProfissional p = basico(nome, email, cpf, TipoProfissional.MEDICO, idAdminCriador);
        p.crm          = crm;
        p.ufCrm        = ufCrm;
        p.especialidade = especialidade;
        return p;
    }

    public static PreCadastroProfissional criarParaEnfermeiro(
            String nome,
            String email,
            String cpf,
            String coren,
            String ufCoren,
            CategoriaCoren categoriaCoren,
            Long idAdminCriador) {

        PreCadastroProfissional p = basico(nome, email, cpf, TipoProfissional.ENFERMEIRO, idAdminCriador);
        p.coren         = coren;
        p.ufCoren       = ufCoren;
        p.categoriaCoren = categoriaCoren;
        return p;
    }

    private static PreCadastroProfissional basico(
            String nome, String email, String cpf,
            TipoProfissional tipo, Long idAdminCriador) {

        PreCadastroProfissional p = new PreCadastroProfissional();
        p.nome            = nome;
        p.email           = email;
        p.cpf             = cpf;
        p.tipoProfissional = tipo;
        p.idAdminCriador  = idAdminCriador;
        p.dataCriacao     = LocalDateTime.now();
        p.status          = StatusPreCadastro.PENDENTE;
        p.tokenConvite    = UUID.randomUUID().toString();
        p.emailEnviado    = false;
        return p;
    }

    public void registrarEnvioEmail() {
        this.emailEnviado   = true;
        this.dataEnvioEmail = LocalDateTime.now();
    }

    public void concluir() {
        this.status = StatusPreCadastro.CONCLUIDO;
    }

    public void expirar() {
        this.status = StatusPreCadastro.EXPIRADO;
    }

    public boolean isPendente() {
        return StatusPreCadastro.PENDENTE.equals(this.status);
    }

    public boolean isMedico() {
        return TipoProfissional.MEDICO.equals(this.tipoProfissional);
    }

    public boolean isEnfermeiro() {
        return TipoProfissional.ENFERMEIRO.equals(this.tipoProfissional);
    }

    public String getDataCriacaoFormatada() {
        if (dataCriacao == null) return "";
        return dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getDataEnvioEmailFormatada() {
        if (dataEnvioEmail == null) return "";
        return dataEnvioEmail.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
