package br.com.hsg.service.mail;

import br.com.hsg.domain.enums.TipoProfissional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class MailService {

    private static final Logger LOG = Logger.getLogger(MailService.class.getName());

    private static final String BASE_URL  = env("HSG_BASE_URL", "http://localhost:8180/hsg-his");

    private static final String REMETENTE = firstNonBlank(
            env("MAIL_SMTP_FROM", null),
            env("HSG_EMAIL_REMETENTE", null),
            "noreply@hsg-his.com.br");

    private Session jndiSession;

    @PostConstruct
    private void init() {
        try {
            jndiSession = (Session) new InitialContext().lookup("java:jboss/mail/Default");
            LOG.info("[MailService] Sessão JNDI 'java:jboss/mail/Default' carregada com sucesso.");
        } catch (Exception e) {
            jndiSession = null;
            LOG.warning("[MailService] JNDI mail-session não encontrada — e-mails usarão sessão programática via variáveis de ambiente (DEV fallback).");
        }
    }

    public void enviarConviteProfissional(
            String nome,
            String emailPessoal,
            TipoProfissional tipo,
            String token,
            String emailCorporativo) {

        String assunto = "Convite de cadastro — HSG Hospital Information System";
        String corpo   = montarCorpoConvite(nome, tipo, token, emailCorporativo);
        enviar(emailPessoal, assunto, corpo);
    }

    private void enviar(String destinatario, String assunto, String corpo) {
        boolean usandoJndi = (jndiSession != null);
        Session session    = usandoJndi ? jndiSession : buildFallbackSession();

        try {
            MimeMessage mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(REMETENTE));
            mensagem.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mensagem.setSubject(assunto, "UTF-8");
            mensagem.setContent(corpo, "text/html; charset=UTF-8");
            Transport.send(mensagem);
            LOG.info("[MailService] E-mail enviado para " + destinatario
                    + " via " + (usandoJndi ? "JNDI" : "sessão programática") + ".");
        } catch (MessagingException e) {
            LOG.log(Level.SEVERE, "[MailService] Falha ao enviar para " + destinatario, e);
            throw new RuntimeException("Não foi possível enviar o e-mail de convite.", e);
        }
    }

    private Session buildFallbackSession() {
        String  host = env("MAIL_SMTP_HOST",     "localhost");
        String  port = env("MAIL_SMTP_PORT",     "1025");
        String  user = env("MAIL_SMTP_USER",     null);
        String  pass = env("MAIL_SMTP_PASS",     null);
        boolean auth = Boolean.parseBoolean(env("MAIL_SMTP_AUTH",     "false"));
        boolean tls  = Boolean.parseBoolean(env("MAIL_SMTP_STARTTLS", "false"));

        Properties props = new Properties();
        props.put("mail.smtp.host",              host);
        props.put("mail.smtp.port",              port);
        props.put("mail.smtp.auth",              String.valueOf(auth));
        props.put("mail.smtp.starttls.enable",   String.valueOf(tls));
        if (tls) {
            props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
        }

        LOG.info("[MailService] Sessão programática: host=" + host + " port=" + port
                + " auth=" + auth + " starttls=" + tls);

        if (auth && user != null && pass != null) {
            final String finalUser = user;
            final String finalPass = pass;
            return Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(finalUser, finalPass);
                }
            });
        }
        return Session.getInstance(props);
    }

    private String montarCorpoConvite(
            String nome, TipoProfissional tipo, String token, String emailCorporativo) {

        String linkAtivacao     = BASE_URL + "/public/ativacao-profissional.xhtml?token=" + token;
        String tipoProfissional = tipo.getDescricao();

        String blocoEmailCorp = emailCorporativo != null
                ? "<div style='background:#f0f7ff;border:1px solid #cce0ff;border-radius:4px;padding:12px 16px;margin:20px 0;'>"
                  + "<p style='margin:0 0 4px;font-size:12px;color:#555;'>Seu e-mail corporativo (login no sistema) será:</p>"
                  + "<p style='margin:0;font-size:15px;font-weight:bold;color:#1a6b8a;font-family:monospace;'>"
                  + emailCorporativo + "</p>"
                  + "</div>"
                : "";

        return "<!DOCTYPE html><html><head><meta charset='UTF-8'/></head>"
                + "<body style='font-family:Arial,sans-serif;color:#333;'>"
                + "<div style='max-width:600px;margin:40px auto;padding:32px;border:1px solid #e0e0e0;border-radius:8px;'>"
                + "<h2 style='color:#1a6b8a;'>Convite de cadastro — HSG HIS</h2>"
                + "<p>Olá, <strong>" + nome + "</strong>.</p>"
                + "<p>Você foi convidado(a) pela administração do sistema HSG para completar seu cadastro como <strong>" + tipoProfissional + "</strong>.</p>"
                + blocoEmailCorp
                + "<p>Clique no botão abaixo para acessar o formulário de cadastro:</p>"
                + "<p style='text-align:center;margin:32px 0;'>"
                + "<a href='" + linkAtivacao + "' style='background:#1a6b8a;color:#fff;padding:12px 28px;border-radius:4px;text-decoration:none;font-weight:bold;'>Completar cadastro</a>"
                + "</p>"
                + "<p style='font-size:12px;color:#888;'>Caso não consiga clicar no botão, copie e cole o link abaixo no seu navegador:<br/>"
                + "<a href='" + linkAtivacao + "' style='color:#1a6b8a;'>" + linkAtivacao + "</a></p>"
                + "<hr style='border:none;border-top:1px solid #eee;margin:24px 0;'/>"
                + "<p style='font-size:11px;color:#aaa;'>Este convite é de uso único e expira em 2 dias. Se você não esperava receber este e-mail, ignore-o.</p>"
                + "</div></body></html>";
    }

    private static String env(String key, String defaultValue) {
        String val = System.getenv(key);
        return (val != null && !val.trim().isEmpty()) ? val.trim() : defaultValue;
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.trim().isEmpty()) return v.trim();
        }
        return "";
    }
}
