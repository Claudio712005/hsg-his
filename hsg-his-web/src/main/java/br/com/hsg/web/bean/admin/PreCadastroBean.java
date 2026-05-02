package br.com.hsg.web.bean.admin;

import br.com.hsg.domain.entity.PreCadastroProfissional;
import br.com.hsg.domain.enums.CategoriaCoren;
import br.com.hsg.domain.enums.StatusPreCadastro;
import br.com.hsg.domain.enums.TipoProfissional;
import br.com.hsg.service.facade.admin.PreCadastroServiceFacade;
import br.com.hsg.web.bean.session.BeanSessao;
import br.com.hsg.web.dto.form.PreCadastroFormDTO;
import br.com.hsg.web.util.JSFUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("preCadastroBean")
public class PreCadastroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject private PreCadastroServiceFacade preCadastroService;
    @Inject private BeanSessao               beanSessao;

    private PreCadastroFormDTO form;
    private List<PreCadastroProfissional> lista;

    @PostConstruct
    public void init() {
        garantirAdmin();
        form = new PreCadastroFormDTO();
        carregarLista();
    }

    public void salvar() {
        garantirAdmin();
        try {
            Long idAdmin = beanSessao.getAdmin().getId();

            if (form.isMedico()) {
                preCadastroService.criarParaMedico(
                        form.getNome(),
                        form.getEmail(),
                        form.getCpf(),
                        form.getCrm(),
                        form.getUfCrm(),
                        form.getEspecialidade(),
                        idAdmin);
            } else if (form.isEnfermeiro()) {
                preCadastroService.criarParaEnfermeiro(
                        form.getNome(),
                        form.getEmail(),
                        form.getCpf(),
                        form.getCoren(),
                        form.getUfCoren(),
                        form.getCategoriaCoren(),
                        idAdmin);
            } else {
                JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_WARN,
                        "Selecione o tipo de profissional.");
                return;
            }

            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_INFO,
                    "Pré-cadastro criado com sucesso. O convite será enviado por e-mail.");
            form.limpar();
            carregarLista();

        } catch (IllegalArgumentException e) {
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_WARN, e.getMessage());
        } catch (IllegalStateException e) {
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_WARN, e.getMessage());
        }
    }

    public void enviarConvite(Long preCadastroId) {
        garantirAdmin();
        try {
            preCadastroService.enviarConvite(preCadastroId);
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_INFO,
                    "Convite enviado com sucesso.");
            carregarLista();
        } catch (IllegalStateException e) {
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_WARN, e.getMessage());
        } catch (RuntimeException e) {
            JSFUtil.adicionarMensagem(null, FacesMessage.SEVERITY_ERROR,
                    "Falha ao enviar e-mail. Verifique as configurações de SMTP.");
        }
    }

    public void limparFormulario() {
        form.limpar();
    }

    public TipoProfissional[] getTiposProfissional() {
        return TipoProfissional.values();
    }

    public CategoriaCoren[] getCategoriasCoren() {
        return CategoriaCoren.values();
    }

    public long getTotalPendentes() {
        return preCadastroService.contarPorStatus(StatusPreCadastro.PENDENTE);
    }

    private void carregarLista() {
        lista = preCadastroService.listarTodos(0, 50);
    }

    private void garantirAdmin() {
        if (!beanSessao.isLogadoComoAdmin()) {
            throw new SecurityException("Acesso negado: funcionalidade restrita a administradores.");
        }
    }

    public PreCadastroFormDTO getForm()                    { return form; }
    public List<PreCadastroProfissional> getLista()        { return lista; }
}
