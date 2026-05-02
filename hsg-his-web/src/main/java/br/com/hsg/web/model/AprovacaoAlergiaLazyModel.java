package br.com.hsg.web.model;

import br.com.hsg.domain.entity.Alergia;
import br.com.hsg.service.facade.paciente.AlergiaServiceFacade;
import br.com.hsg.web.dto.response.AprovacaoAlergiaDTO;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AprovacaoAlergiaLazyModel extends LazyDataModel<AprovacaoAlergiaDTO> {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final AlergiaServiceFacade service;

    public AprovacaoAlergiaLazyModel(AlergiaServiceFacade service) {
        this.service = service;
    }

    @Override
    public List<AprovacaoAlergiaDTO> load(int first, int pageSize, String sortField,
                                          SortOrder sortOrder, Map<String, Object> filters) {
        setRowCount((int) service.contarParaAprovacao());
        List<Alergia> alergias = service.listarParaAprovacao(first, pageSize);
        List<AprovacaoAlergiaDTO> dtos = new ArrayList<>();
        for (Alergia a : alergias) {
            dtos.add(toDTO(a));
        }
        return dtos;
    }

    private AprovacaoAlergiaDTO toDTO(Alergia a) {
        AprovacaoAlergiaDTO dto = new AprovacaoAlergiaDTO();
        dto.setId(a.getId());
        dto.setPacienteId(a.getPaciente().getId());
        dto.setNomePaciente(a.getPaciente().getNomeCompleto());
        dto.setNomeAlergia(a.getNome());
        dto.setTipoDescricao(a.getTipoAlergia() != null ? a.getTipoAlergia().getDescricao() : "—");
        dto.setGravidadeDescricao(a.getGravidadeAlergia() != null ? a.getGravidadeAlergia().getDescricao() : "—");
        dto.setGravidadeCssClass(gravCss(a));
        dto.setObservacao(a.getObservacao());
        dto.setDataCadastro(a.getDataCadastro() != null ? a.getDataCadastro().format(FMT) : "—");
        return dto;
    }

    private String gravCss(Alergia a) {
        if (a.getGravidadeAlergia() == null) return "";
        switch (a.getGravidadeAlergia()) {
            case A: return "badge-anafilatica";
            case G: return "badge-grave";
            case M: return "badge-moderada";
            case L: return "badge-leve";
            default: return "";
        }
    }
}
