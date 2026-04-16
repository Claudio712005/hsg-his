package br.com.hsg.domain.vo;

import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DataNascimento {

    @Getter
    private LocalDate valor;

    protected DataNascimento() {}

    public DataNascimento(LocalDate valor) {
        LocalDate hoje = LocalDate.now();

        if(valor.isBefore(hoje)) {
            throw new IllegalArgumentException("Data de nascimento deve ser anterior à data atual");
        }

        if(hoje.minusYears(18).isBefore(valor)) {
            throw new IllegalArgumentException("Paciente deve ser maior de 18 anos");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataNascimento that = (DataNascimento) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
