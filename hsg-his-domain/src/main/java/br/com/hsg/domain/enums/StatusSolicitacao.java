package br.com.hsg.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum StatusSolicitacao {

    P("Pendente"),
    A("Aprovada"),
    R("Rejeitada");

    private String descricao;
}
