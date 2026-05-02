package br.com.hsg.service.impl.admin;

import br.com.hsg.dao.PreCadastroProfissionalDAO;
import br.com.hsg.domain.entity.PreCadastroProfissional;
import br.com.hsg.domain.enums.CategoriaCoren;
import br.com.hsg.domain.enums.StatusPreCadastro;
import br.com.hsg.domain.enums.TipoProfissional;
import br.com.hsg.service.facade.admin.PreCadastroServiceFacade;
import br.com.hsg.service.mail.MailService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PreCadastroServiceImpl implements PreCadastroServiceFacade {

    @EJB private PreCadastroProfissionalDAO preCadastroDAO;
    @EJB private MailService                mailService;

    @Override
    public PreCadastroProfissional criarParaMedico(
            String nome,
            String email,
            String cpf,
            String crm,
            String ufCrm,
            String especialidade,
            Long idAdminCriador) {

        validarCamposComuns(nome, email, cpf, idAdminCriador);
        validarCamposMedico(crm, ufCrm);
        validarDuplicidade(email, cpf);

        PreCadastroProfissional preCadastro = PreCadastroProfissional.criarParaMedico(
                nome.trim(), email.trim().toLowerCase(), cpf.trim(),
                crm.trim(), ufCrm.trim().toUpperCase(), especialidade,
                idAdminCriador);

        return preCadastroDAO.salvar(preCadastro);
    }

    @Override
    public PreCadastroProfissional criarParaEnfermeiro(
            String nome,
            String email,
            String cpf,
            String coren,
            String ufCoren,
            CategoriaCoren categoriaCoren,
            Long idAdminCriador) {

        validarCamposComuns(nome, email, cpf, idAdminCriador);
        validarCamposEnfermeiro(coren, ufCoren, categoriaCoren);
        validarDuplicidade(email, cpf);

        PreCadastroProfissional preCadastro = PreCadastroProfissional.criarParaEnfermeiro(
                nome.trim(), email.trim().toLowerCase(), cpf.trim(),
                coren.trim(), ufCoren.trim().toUpperCase(), categoriaCoren,
                idAdminCriador);

        return preCadastroDAO.salvar(preCadastro);
    }

    @Override
    public void enviarConvite(Long preCadastroId) {
        PreCadastroProfissional preCadastro = requerer(preCadastroId);

        if (!preCadastro.isPendente()) {
            throw new IllegalStateException("Convite só pode ser enviado para pré-cadastros com status PENDENTE.");
        }

        mailService.enviarConviteProfissional(
                preCadastro.getNome(),
                preCadastro.getEmail(),
                preCadastro.getTipoProfissional(),
                preCadastro.getTokenConvite());

        preCadastro.registrarEnvioEmail();
        preCadastroDAO.atualizar(preCadastro);
    }

    @Override
    public PreCadastroProfissional buscarPorToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token inválido.");
        }
        return preCadastroDAO.buscarPorToken(token.trim());
    }

    @Override
    public PreCadastroProfissional buscarPorId(Long id) {
        return preCadastroDAO.buscarPorId(id);
    }

    @Override
    public List<PreCadastroProfissional> listarTodos(int inicio, int tamanho) {
        return preCadastroDAO.listarTodos(inicio, tamanho);
    }

    @Override
    public List<PreCadastroProfissional> listarPorStatus(StatusPreCadastro status, int inicio, int tamanho) {
        return preCadastroDAO.listarPorStatus(status, inicio, tamanho);
    }

    @Override
    public List<PreCadastroProfissional> listarPorTipo(TipoProfissional tipo, int inicio, int tamanho) {
        return preCadastroDAO.listarPorTipo(tipo, inicio, tamanho);
    }

    @Override
    public long contar() {
        return preCadastroDAO.contar();
    }

    @Override
    public long contarPorStatus(StatusPreCadastro status) {
        return preCadastroDAO.contarPorStatus(status);
    }

    private void validarCamposComuns(String nome, String email, String cpf, Long idAdmin) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }
        if (idAdmin == null) {
            throw new IllegalArgumentException("Administrador criador é obrigatório.");
        }
    }

    private void validarCamposMedico(String crm, String ufCrm) {
        if (crm == null || crm.trim().isEmpty()) {
            throw new IllegalArgumentException("CRM é obrigatório para médicos.");
        }
        if (ufCrm == null || ufCrm.trim().isEmpty()) {
            throw new IllegalArgumentException("UF do CRM é obrigatória para médicos.");
        }
    }

    private void validarCamposEnfermeiro(String coren, String ufCoren, CategoriaCoren categoria) {
        if (coren == null || coren.trim().isEmpty()) {
            throw new IllegalArgumentException("COREN é obrigatório para enfermeiros.");
        }
        if (ufCoren == null || ufCoren.trim().isEmpty()) {
            throw new IllegalArgumentException("UF do COREN é obrigatória para enfermeiros.");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria do COREN é obrigatória para enfermeiros.");
        }
    }

    private void validarDuplicidade(String email, String cpf) {
        if (preCadastroDAO.existeEmailPendente(email.trim().toLowerCase())) {
            throw new IllegalStateException("Já existe um pré-cadastro pendente para este e-mail.");
        }
        if (preCadastroDAO.existeCpfPendente(cpf.trim())) {
            throw new IllegalStateException("Já existe um pré-cadastro pendente para este CPF.");
        }
    }

    private PreCadastroProfissional requerer(Long id) {
        PreCadastroProfissional p = preCadastroDAO.buscarPorId(id);
        if (p == null) throw new IllegalArgumentException("Pré-cadastro não encontrado.");
        return p;
    }
}
