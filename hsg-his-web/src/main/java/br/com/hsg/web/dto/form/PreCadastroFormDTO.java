package br.com.hsg.web.dto.form;

import br.com.hsg.domain.enums.CategoriaCoren;
import br.com.hsg.domain.enums.TipoProfissional;
import lombok.Data;

import java.io.Serializable;

@Data
public class PreCadastroFormDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private TipoProfissional tipoProfissional;

    // campos comuns
    private String nome;
    private String email;
    private String cpf;

    // campos de médico
    private String crm;
    private String ufCrm;
    private String especialidade;

    // campos de enfermeiro
    private String coren;
    private String ufCoren;
    private CategoriaCoren categoriaCoren;

    public void limpar() {
        tipoProfissional = null;
        nome = email = cpf = null;
        crm = ufCrm = especialidade = null;
        coren = ufCoren = null;
        categoriaCoren = null;
    }

    public boolean isMedico() {
        return TipoProfissional.MEDICO.equals(tipoProfissional);
    }

    public boolean isEnfermeiro() {
        return TipoProfissional.ENFERMEIRO.equals(tipoProfissional);
    }
}
