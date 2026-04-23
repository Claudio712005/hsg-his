package br.com.hsg.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "TB_PL_ADES", schema = "his")
public class PlanoAdesao {

    @Id
    @Getter
    @Column(name = "ID_PL_ADES")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PL_ADESAO", sequenceName = "SQ_PL_ADESAO", allocationSize = 1)
    private Long id;


}
