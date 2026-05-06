package br.com.hsg.dao;

import br.com.hsg.domain.entity.Especialidade;
import br.com.hsg.domain.enums.IndicativoStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EspecialidadeDAO {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    public Especialidade buscarPorId(Long id) {
        return em.find(Especialidade.class, id);
    }

    public Especialidade buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return null;
        String nomeNorm = nome.trim();

        List<Especialidade> exatos = em.createQuery(
                "SELECT e FROM Especialidade e WHERE LOWER(e.nome) = LOWER(:nome)",
                Especialidade.class)
                .setParameter("nome", nomeNorm)
                .getResultList();
        if (!exatos.isEmpty()) return exatos.get(0);

        String nomeNormLower = nomeNorm.toLowerCase();
        List<Especialidade> todas = em.createQuery(
                "SELECT e FROM Especialidade e WHERE e.status = :st ORDER BY LENGTH(e.nome) DESC",
                Especialidade.class)
                .setParameter("st", IndicativoStatus.A)
                .getResultList();
        for (Especialidade e : todas) {
            if (nomeNormLower.startsWith(e.getNome().trim().toLowerCase())) {
                return e;
            }
        }

        return null;
    }

    public List<Especialidade> listarAtivas() {
        return em.createQuery(
                "SELECT e FROM Especialidade e WHERE e.status = :st ORDER BY e.nome ASC",
                Especialidade.class)
                .setParameter("st", IndicativoStatus.A)
                .getResultList();
    }

    public void salvar(Especialidade especialidade) {
        em.persist(especialidade);
    }
}
