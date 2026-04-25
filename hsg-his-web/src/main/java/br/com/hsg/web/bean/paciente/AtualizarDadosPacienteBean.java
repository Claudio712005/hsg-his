package br.com.hsg.web.bean.paciente;

import br.com.hsg.domain.entity.Endereco;
import br.com.hsg.domain.enums.TipoSanguineoEnum;
import br.com.hsg.domain.enums.TipoSolicitacao;
import br.com.hsg.service.facade.paciente.SolicitacaoAtualizacaoServiceFacade;
import br.com.hsg.web.bean.session.BeanSessao;
import br.com.hsg.web.dto.form.AtualizacaoCadastralDTO;
import br.com.hsg.web.dto.form.AtualizacaoClinicaDTO;
import br.com.hsg.web.dto.form.AtualizacaoEnderecoDTO;
import br.com.hsg.web.dto.response.PacienteResponseDTO;
import br.com.hsg.web.model.HistoricoLazyModel;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ViewScoped
@Named("atualizarDadosPacienteBean")
public class AtualizarDadosPacienteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private SolicitacaoAtualizacaoServiceFacade solicitacaoService;

    @Inject
    private BeanSessao beanSessao;

    private AtualizacaoCadastralDTO formCadastral;
    private AtualizacaoEnderecoDTO  formEndereco;
    private AtualizacaoClinicaDTO   formClinico;

    private HistoricoLazyModel historicoCadastralModel;
    private HistoricoLazyModel historicoClinicoModel;

    private List<TipoSanguineoEnum> tiposSanguineos;

    @PostConstruct
    public void init() {
        formCadastral   = new AtualizacaoCadastralDTO();
        formEndereco    = new AtualizacaoEnderecoDTO();
        formClinico     = new AtualizacaoClinicaDTO();
        tiposSanguineos = Arrays.asList(TipoSanguineoEnum.values());

        PacienteResponseDTO paciente = beanSessao.getPaciente();
        Long pacienteId = paciente != null ? paciente.getId() : null;

        preencherFormCadastral(paciente);
        preencherFormEndereco(pacienteId);

        List<TipoSolicitacao> tiposCadastrais = Arrays.asList(TipoSolicitacao.CADASTRAL, TipoSolicitacao.ENDERECO);
        List<TipoSolicitacao> tiposClinico    = Collections.singletonList(TipoSolicitacao.CLINICO);

        historicoCadastralModel = new HistoricoLazyModel(solicitacaoService, pacienteId, tiposCadastrais);
        historicoClinicoModel   = new HistoricoLazyModel(solicitacaoService, pacienteId, tiposClinico);
    }

    private void preencherFormCadastral(PacienteResponseDTO paciente) {
        if (paciente == null) return;
        String nomeCompleto = paciente.getNomeCompleto();
        if (nomeCompleto != null && !nomeCompleto.trim().isEmpty()) {
            int idx = nomeCompleto.indexOf(' ');
            formCadastral.setPrimeiroNome(idx > 0 ? nomeCompleto.substring(0, idx) : nomeCompleto);
            formCadastral.setSobrenome(idx > 0 ? nomeCompleto.substring(idx + 1) : "");
        }
        formCadastral.setEmail(paciente.getEmail());
        formCadastral.setTelefone(paciente.getTelefone());
    }

    private void preencherFormEndereco(Long pacienteId) {
        if (pacienteId == null) return;
        try {
            Endereco endereco = solicitacaoService.buscarEnderecoPorPaciente(pacienteId);
            if (endereco != null) {
                formEndereco.setLogradouro(endereco.getLogradouro());
                formEndereco.setNumero(endereco.getNumero());
                formEndereco.setComplemento(endereco.getComplemento());
                formEndereco.setBairro(endereco.getBairro());
                formEndereco.setCidade(endereco.getCidade());
                formEndereco.setEstado(endereco.getEstado());
                formEndereco.setCep(endereco.getCep());
            }
        } catch (Exception ignored) {
        }
    }

    public void solicitarAtualizacaoCadastral() {
        try {
            solicitacaoService.solicitarAtualizacaoCadastral(
                    pacienteId(),
                    formCadastral.getPrimeiroNome(),
                    formCadastral.getSobrenome(),
                    formCadastral.getEmail(),
                    formCadastral.getTelefone(),
                    formCadastral.getMotivo()
            );
            mensagem(FacesMessage.SEVERITY_INFO, "Solicitação registrada",
                    "Sua solicitação cadastral foi enviada e aguarda análise.");
            formCadastral.setMotivo(null);
            historicoCadastralModel.setRowCount(0);
        } catch (IllegalArgumentException e) {
            mensagem(FacesMessage.SEVERITY_WARN, "Dados inválidos", e.getMessage());
        } catch (Exception e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro ao registrar",
                    e.getMessage());
        }
    }

    public void solicitarAtualizacaoEndereco() {
        try {
            solicitacaoService.solicitarAtualizacaoEndereco(
                    pacienteId(),
                    formEndereco.getLogradouro(),
                    formEndereco.getNumero(),
                    formEndereco.getComplemento(),
                    formEndereco.getBairro(),
                    formEndereco.getCidade(),
                    formEndereco.getEstado(),
                    formEndereco.getCep(),
                    formEndereco.getMotivo()
            );
            mensagem(FacesMessage.SEVERITY_INFO, "Solicitação registrada",
                    "Sua solicitação de endereço foi enviada e aguarda análise.");
            formEndereco.setMotivo(null);
            historicoCadastralModel.setRowCount(0);
        } catch (IllegalArgumentException e) {
            mensagem(FacesMessage.SEVERITY_WARN, "Dados inválidos", e.getMessage());
        } catch (Exception e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro ao registrar",
                    e.getMessage());
        }
    }

    public void solicitarAtualizacaoClinica() {
        try {
            solicitacaoService.solicitarAtualizacaoClinica(
                    pacienteId(),
                    formClinico.getPeso(),
                    formClinico.getAltura(),
                    formClinico.getTipoSanguineo(),
                    formClinico.getMotivo()
            );

            mensagem(FacesMessage.SEVERITY_INFO, "Solicitação registrada",
                    "Sua solicitação clínica foi enviada e aguarda análise.");
            formClinico = new AtualizacaoClinicaDTO();
            historicoClinicoModel.setRowCount(0);
        } catch (IllegalArgumentException e) {
            mensagem(FacesMessage.SEVERITY_WARN, "Dados inválidos", e.getMessage());
        } catch (Exception e) {
            mensagem(FacesMessage.SEVERITY_ERROR, "Erro ao registrar",
                    "Não foi possível registrar a solicitação. Tente novamente.");
        }
    }

    private Long pacienteId() {
        PacienteResponseDTO p = beanSessao.getPaciente();
        if (p == null) throw new IllegalArgumentException("Sessão inválida. Faça login novamente.");
        return p.getId();
    }

    private void mensagem(FacesMessage.Severity severidade, String resumo, String detalhe) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severidade, resumo, detalhe));
    }

    public AtualizacaoCadastralDTO getFormCadastral() { return formCadastral; }
    public AtualizacaoEnderecoDTO  getFormEndereco()  { return formEndereco; }
    public AtualizacaoClinicaDTO   getFormClinico()   { return formClinico; }

    public HistoricoLazyModel getHistoricoCadastralModel() { return historicoCadastralModel; }
    public HistoricoLazyModel getHistoricoClinicoModel()   { return historicoClinicoModel; }

    public List<TipoSanguineoEnum> getTiposSanguineos() { return tiposSanguineos; }
}
