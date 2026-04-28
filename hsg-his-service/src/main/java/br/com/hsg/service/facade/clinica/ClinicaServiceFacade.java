package br.com.hsg.service.facade.clinica;

import br.com.hsg.domain.entity.Enfermeiro;
import br.com.hsg.domain.entity.Medico;

import javax.ejb.Local;

@Local
public interface ClinicaServiceFacade {

    Enfermeiro buscarEnfermeiroPorKeycloakId(String keycloakId);

    Medico buscarMedicoPorKeycloakId(String keycloakId);
}
