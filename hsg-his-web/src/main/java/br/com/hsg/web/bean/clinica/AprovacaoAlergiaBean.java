package br.com.hsg.web.bean.clinica;

import br.com.hsg.service.facade.paciente.AlergiaServiceFacade;
import br.com.hsg.web.bean.session.BeanSessao;
import br.com.hsg.web.dto.response.AprovacaoAlergiaDTO;
import br.com.hsg.web.model.AprovacaoAlergiaLazyModel;
import br.com.hsg.web.util.JSFUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("aprovacaoAlergiaBean")
public class AprovacaoAlergiaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject private AlergiaServiceFacade alergiaService;
    @Inject private BeanSessao           beanSessao;

    private AprovacaoAlergiaLazyModel model;
    private AprovacaoAlergiaDTO        alergiaEmAnalise;
    private String                     observacaoAprovador;

    @PostConstruct
    public void init() {
        model = new AprovacaoAlergiaLazyModel(alergiaService);
    }

    public void prepararAnalise(AprovacaoAlergiaDTO dto) {
        this.alergiaEmAnalise    = dto;
        this.observacaoAprovador = null;
    }

    public void confirmarAprovacao() {
        if (alergiaEmAnalise == null) return;
        try {
            alergiaService.aprovarAlergia(
                    alergiaEmAnalise.getId(),
                    beanSessao.getUsuarioClinica().getId(),
                    observacaoAprovador);
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_INFO,
                    "Alergia aprovada com sucesso.");
            model = new AprovacaoAlergiaLazyModel(alergiaService);
            alergiaEmAnalise    = null;
            observacaoAprovador = null;
        } catch (IllegalStateException e) {
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_WARN, e.getMessage());
        }
    }

    public void confirmarRejeicao() {
        if (alergiaEmAnalise == null) return;
        try {
            alergiaService.rejeitarAlergia(
                    alergiaEmAnalise.getId(),
                    beanSessao.getUsuarioClinica().getId(),
                    observacaoAprovador);
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_INFO,
                    "Alergia rejeitada.");
            model = new AprovacaoAlergiaLazyModel(alergiaService);
            alergiaEmAnalise    = null;
            observacaoAprovador = null;
        } catch (IllegalStateException e) {
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_WARN, e.getMessage());
        }
    }

    public AprovacaoAlergiaLazyModel getModel()                  { return model; }
    public AprovacaoAlergiaDTO getAlergiaEmAnalise()             { return alergiaEmAnalise; }
    public String getObservacaoAprovador()                       { return observacaoAprovador; }
    public void setObservacaoAprovador(String obs)               { this.observacaoAprovador = obs; }
}
