package br.com.hsg.service.impl.clinica;

import br.com.hsg.dao.EnfermeiroDAO;
import br.com.hsg.dao.MedicoDAO;
import br.com.hsg.domain.entity.Enfermeiro;
import br.com.hsg.domain.entity.Medico;
import br.com.hsg.service.facade.clinica.ClinicaServiceFacade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ClinicaServiceImpl implements ClinicaServiceFacade {

    @EJB private EnfermeiroDAO enfermeiroDAO;
    @EJB private MedicoDAO     medicoDAO;

    @Override
    public Enfermeiro buscarEnfermeiroPorKeycloakId(String keycloakId) {
        return enfermeiroDAO.buscarPorKeycloakId(keycloakId);
    }

    @Override
    public Medico buscarMedicoPorKeycloakId(String keycloakId) {
        return medicoDAO.buscarPorKeycloakId(keycloakId);
    }
}
