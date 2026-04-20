package br.com.hsg.service.impl;

import br.com.hsg.dao.PacienteDAO;
import br.com.hsg.domain.entity.Paciente;
import br.com.hsg.service.facade.PacienteServiceFacade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PacienteServiceImpl implements PacienteServiceFacade {

    @EJB
    private PacienteDAO pacienteDAO;

    @Override
    public Paciente buscarPorKeycloakId(String keycloakId) {
        return pacienteDAO.buscarPorKeycloakId(keycloakId);
    }
}
