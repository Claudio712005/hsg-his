package br.com.hsg.domain.vo;

import lombok.Getter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverride(name = "valor", column = @Column(name = "EMAIL_PAC"))
public class Email {

    @Getter
    private String valor;

    protected Email() {}

    public Email(String valor) {
        if (valor == null || !valor.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.valor = valor;
    }
}