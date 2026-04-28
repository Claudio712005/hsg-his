package br.com.hsg.dao;

import br.com.hsg.domain.entity.Alergia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AlergiaDAO {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    public Alergia salvar(Alergia alergia) {
        em.persist(alergia);
        em.flush();
        return alergia;
    }

    public Alergia atualizar(Alergia alergia) {
        Alergia merged = em.merge(alergia);
        em.flush();
        return merged;
    }

    public void excluir(Alergia alergia) {
        em.remove(em.contains(alergia) ? alergia : em.merge(alergia));
        em.flush();
    }

    public Alergia buscarPorId(Long id) {
        try {
            return em.createQuery(
                    "SELECT a FROM Alergia a WHERE a.id = :id", Alergia.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public List<Alergia> listarPorPaciente(Long pacienteId, int inicio, int tamanho) {
        return em.createQuery(
                "SELECT a FROM Alergia a WHERE a.paciente.id = :pacienteId ORDER BY a.dataCadastro DESC",
                Alergia.class)
                .setParameter("pacienteId", pacienteId)
                .setFirstResult(inicio)
                .setMaxResults(tamanho)
                .getResultList();
    }

    public long contarPorPaciente(Long pacienteId) {
        return em.createQuery(
                "SELECT COUNT(a) FROM Alergia a WHERE a.paciente.id = :pacienteId", Long.class)
                .setParameter("pacienteId", pacienteId)
                .getSingleResult();
    }
}
