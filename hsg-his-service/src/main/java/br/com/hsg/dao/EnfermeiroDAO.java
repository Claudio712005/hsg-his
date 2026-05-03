package br.com.hsg.dao;

import br.com.hsg.domain.entity.Enfermeiro;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class EnfermeiroDAO {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    public Enfermeiro buscarPorId(Long id) {
        try {
            return em.createQuery(
                    "SELECT e FROM Enfermeiro e JOIN FETCH e.contaUsuario WHERE e.id = :id",
                    Enfermeiro.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Enfermeiro buscarPorKeycloakId(String keycloakId) {
        try {
            return em.createQuery(
                    "SELECT e FROM Enfermeiro e JOIN FETCH e.contaUsuario c WHERE c.keycloakId = :kcId",
                    Enfermeiro.class)
                    .setParameter("kcId", keycloakId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
