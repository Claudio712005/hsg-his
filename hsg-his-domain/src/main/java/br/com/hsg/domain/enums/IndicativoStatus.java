package br.com.hsg.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum IndicativoStatus {

    A("A", "Ativo"),
    I("I", "Inativo"),
    S("S", "Suspenso"),
    E("E", "Excluído");

    private String valor;
    private String descricao;

    public static IndicativoStatus getIndicativoStatus(String valor) {
        for (IndicativoStatus indicativoStatus : IndicativoStatus.values()) {
            if (indicativoStatus.getValor().equalsIgnoreCase(valor)) {
                return indicativoStatus;
            }
        }

        throw new IllegalArgumentException("Valor de IndicativoStatus inválido: " + valor);
    }
}
