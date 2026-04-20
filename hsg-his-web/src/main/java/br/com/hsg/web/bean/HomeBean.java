package br.com.hsg.web.bean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Named("homeBean")
@ViewScoped
public class HomeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final String nomeUsuario;
    private final String dataAtual;
    private final String saudacao;
    private final List<ModuloAcesso> modulos;

    public HomeBean() {
        nomeUsuario  = "Administrador";
        dataAtual    = LocalDate.now().format(FORMATO_DATA);
        saudacao     = resolverSaudacao();
        modulos      = construirModulos();
    }

    private String resolverSaudacao() {
        int hora = LocalTime.now().getHour();
        if (hora < 12) return "Bom dia";
        if (hora < 18) return "Boa tarde";
        return "Boa noite";
    }

    private List<ModuloAcesso> construirModulos() {
        return Arrays.asList(
            new ModuloAcesso("Pacientes",      "pi pi-users",      "Cadastro e gestão de pacientes",         "/pages/pacientes/lista.xhtml"),
            new ModuloAcesso("Internações",    "pi pi-home",       "Controle de internações hospitalares",   "/pages/internacoes/lista.xhtml"),
            new ModuloAcesso("Ambulatório",    "pi pi-calendar",   "Agendamento e consultas ambulatoriais",  "/pages/ambulatorio/agenda.xhtml"),
            new ModuloAcesso("Prontuário",     "pi pi-file",       "Prontuário eletrônico do paciente",      "/pages/prontuario/lista.xhtml"),
            new ModuloAcesso("Farmácia",       "pi pi-shopping-cart","Dispensação e controle de medicamentos","/pages/farmacia/estoque.xhtml"),
            new ModuloAcesso("Laboratório",    "pi pi-chart-bar",  "Solicitação e resultado de exames",      "/pages/laboratorio/solicitacoes.xhtml"),
            new ModuloAcesso("Faturamento",    "pi pi-dollar",     "Faturamento e contas hospitalares",      "/pages/faturamento/lista.xhtml"),
            new ModuloAcesso("Relatórios",     "pi pi-chart-line", "Relatórios gerenciais e indicadores",    "/pages/relatorios/index.xhtml")
        );
    }

    public String getNomeUsuario()  { return nomeUsuario; }
    public String getDataAtual()    { return dataAtual; }
    public String getSaudacao()     { return saudacao; }
    public List<ModuloAcesso> getModulos() { return modulos; }

    public static final class ModuloAcesso implements Serializable {

        private static final long serialVersionUID = 1L;

        private final String titulo;
        private final String icone;
        private final String descricao;
        private final String url;

        public ModuloAcesso(String titulo, String icone, String descricao, String url) {
            this.titulo   = titulo;
            this.icone    = icone;
            this.descricao = descricao;
            this.url      = url;
        }

        public String getTitulo()    { return titulo; }
        public String getIcone()     { return icone; }
        public String getDescricao() { return descricao; }
        public String getUrl()       { return url; }
    }
}
