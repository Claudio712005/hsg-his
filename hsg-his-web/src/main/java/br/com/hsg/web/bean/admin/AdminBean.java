package br.com.hsg.web.bean.admin;

import br.com.hsg.web.bean.session.BeanSessao;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@ViewScoped
@Named("adminBean")
public class AdminBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BeanSessao beanSessao;

    public void sair() throws IOException {
        beanSessao.encerrarSessao();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.invalidateSession();
        ec.redirect(ec.getRequestContextPath() + "/logout");
    }
}
