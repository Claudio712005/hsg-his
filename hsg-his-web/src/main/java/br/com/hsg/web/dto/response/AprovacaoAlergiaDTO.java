package br.com.hsg.web.dto.response;

import lombok.Data;

@Data
public class AprovacaoAlergiaDTO {

    private Long   id;
    private Long   pacienteId;
    private String nomePaciente;
    private String nomeAlergia;
    private String tipoDescricao;
    private String gravidadeDescricao;
    private String gravidadeCssClass;
    private String observacao;
    private String dataCadastro;
}
