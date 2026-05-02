package br.com.hsg.service.impl.clinica;

import br.com.hsg.dao.EnfermeiroDAO;
import br.com.hsg.dao.EspecialidadeDAO;
import br.com.hsg.dao.MedicoDAO;
import br.com.hsg.domain.entity.Enfermeiro;
import br.com.hsg.domain.entity.Especialidade;
import br.com.hsg.domain.entity.Medico;
import br.com.hsg.service.facade.clinica.ClinicaServiceFacade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ClinicaServiceImpl implements ClinicaServiceFacade {

    @EJB private EnfermeiroDAO   enfermeiroDAO;
    @EJB private MedicoDAO       medicoDAO;
    @EJB private EspecialidadeDAO especialidadeDAO;

    @Override
    public Enfermeiro buscarEnfermeiroPorKeycloakId(String keycloakId) {
        return enfermeiroDAO.buscarPorKeycloakId(keycloakId);
    }

    @Override
    public Medico buscarMedicoPorKeycloakId(String keycloakId) {
        return medicoDAO.buscarPorKeycloakId(keycloakId);
    }

    @Override
    public List<Especialidade> listarEspecialidadesAtivas() {
        return especialidadeDAO.listarAtivas();
    }
}
