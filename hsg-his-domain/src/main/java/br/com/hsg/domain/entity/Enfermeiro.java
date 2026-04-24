package br.com.hsg.domain.entity;

import br.com.hsg.domain.vo.NomeCompleto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "TB_ENFER", schema = "hsg")
public class Enfermeiro {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENFER")
    @SequenceGenerator(name = "SEQ_ENFER", sequenceName = "SEQ_ENFER", allocationSize = 1)
    @Column(name = "ID_ENFER")
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "primeiroNome", column = @Column(name = "FRT_NM_ENFER")),
            @AttributeOverride(name = "sobrenome",    column = @Column(name = "LST_NM_ENFER"))
    })
    private NomeCompleto nomeCompleto;

    public String getNomeCompleto() {
        return nomeCompleto != null ? nomeCompleto.getNomeCompleto() : null;
    }
}
