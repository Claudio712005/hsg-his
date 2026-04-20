package br.com.hsg.service.facade;

import br.com.hsg.domain.entity.Paciente;

import javax.ejb.Local;

@Local
public interface PacienteServiceFacade {

    Paciente buscarPorKeycloakId(String keycloakId);
}
