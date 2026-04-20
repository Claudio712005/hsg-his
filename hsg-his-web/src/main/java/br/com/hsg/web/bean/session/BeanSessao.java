package br.com.hsg.web.bean.session;

import br.com.hsg.web.dto.response.PacienteResponseDTO;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named("beanSessao")
public class BeanSessao implements Serializable {

    private static final long serialVersionUID = 1L;

    private PacienteResponseDTO paciente;

}
