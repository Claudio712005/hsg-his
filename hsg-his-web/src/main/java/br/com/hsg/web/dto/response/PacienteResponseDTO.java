package br.com.hsg.web.dto.response;

import lombok.Data;

@Data
public class PacienteResponseDTO {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String username;

}
