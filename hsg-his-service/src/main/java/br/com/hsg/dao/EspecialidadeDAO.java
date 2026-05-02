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
