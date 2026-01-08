package com.javaapi.pmanager.infrastructure.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailBoasVindasHtml(String para, String nome) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String htmlConteudo = """
            <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <div style="background-color: #f3f4f6; padding: 40px 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; width: 100%%;">
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1); overflow: hidden;">
                            <tr>
                                <td style="padding: 40px; text-align: center;">
                                    <h1 style="color: #111827; font-size: 24px; margin-bottom: 10px; margin-top: 0;">
                                        Bem-vindo, %s!
                                    </h1>
        
                                    <p style="color: #4b5563; font-size: 16px; line-height: 1.6; margin-bottom: 30px;">
                                        Ficamos muito felizes em ter você conosco.
                                    </p>
        
                                    <table align="center" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td align="center" style="border-radius: 6px; background-color: #4F46E5;">
                                                <a href="#" target="_blank" style="display: inline-block; padding: 14px 30px; font-size: 16px; color: #ffffff; text-decoration: none; font-weight: 500;">
                                                    Acessar meu Painel
                                                </a>
                                            </td>
                                        </tr>
                                    </table>
        
                                    <hr style="border: 0; border-top: 1px solid #e5e7eb; margin: 40px 0 20px 0;">
        
                                    <footer style="text-align: center;">
                                        <p style="color: #9ca3af; font-size: 12px; margin: 0;">
                                            Vitor Barbosa &copy; 2026
                                        </p>
                                        <p style="color: #9ca3af; font-size: 12px; margin: 5px 0 0 0;">
                                            Você recebeu este e-mail porque se cadastrou em nossa plataforma.
                                        </p>
                                    </footer>
                                </td>
                            </tr>
                        </table>
                    </div>
                </body>
            </html>
            """.formatted(nome);

            helper.setFrom("no-reply@vitorbarbosa.com");
            helper.setTo(para);
            helper.setSubject("Bem-vindo!");
            helper.setText(htmlConteudo, true);

            mailSender.send(mimeMessage);
            System.out.println("E-mail HTML enviado com sucesso!");

        } catch (MessagingException e) {
            System.err.println("Erro ao disparar e-mail: " + e.getMessage());
        }
    }
}
