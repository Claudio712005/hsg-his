package br.com.hsg.dao;

import br.com.hsg.domain.entity.Medico;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class MedicoDAO {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    public Medico buscarPorId(Long id) {
        try {
            return em.createQuery(
                    "SELECT m FROM Medico m JOIN FETCH m.contaUsuario WHERE m.id = :id",
                    Medico.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Medico salvar(Medico medico) {
        em.persist(medico);
        em.flush();
        return medico;
    }

    public Medico buscarPorKeycloakId(String keycloakId) {
        try {
            return em.createQuery(
                    "SELECT m FROM Medico m JOIN FETCH m.contaUsuario c WHERE c.keycloakId = :kcId",
                    Medico.class)
                    .setParameter("kcId", keycloakId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
