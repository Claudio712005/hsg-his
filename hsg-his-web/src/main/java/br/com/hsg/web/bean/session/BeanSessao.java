package br.com.hsg.web.bean.session;

import br.com.hsg.web.dto.response.PacienteResponseDTO;
import br.com.hsg.web.dto.response.UsuarioClinicaDTO;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named("beanSessao")
public class BeanSessao implements Serializable {

    private static final long serialVersionUID = 1L;

    private PacienteResponseDTO paciente;
    private UsuarioClinicaDTO   usuarioClinica;

    public PacienteResponseDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteResponseDTO paciente) {
        this.paciente = paciente;
    }

    public UsuarioClinicaDTO getUsuarioClinica() {
        return usuarioClinica;
    }

    public void setUsuarioClinica(UsuarioClinicaDTO usuarioClinica) {
        this.usuarioClinica = usuarioClinica;
    }

    public boolean isLogado() {
        return paciente != null || usuarioClinica != null;
    }

    public boolean isLogadoComoPaciente() {
        return paciente != null;
    }

    public boolean isLogadoComoClinica() {
        return usuarioClinica != null;
    }

    public void encerrarSessao() {
        paciente       = null;
        usuarioClinica = null;
    }
}
