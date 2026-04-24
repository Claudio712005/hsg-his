package br.com.hsg.web.dto.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class AtualizacaoEnderecoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String motivo;
}
