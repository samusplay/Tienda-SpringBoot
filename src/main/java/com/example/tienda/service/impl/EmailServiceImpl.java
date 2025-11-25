package com.example.tienda.service.impl;

import com.example.tienda.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Override
    public void enviarEmail(String destinatario, String asunto, String cuerpoHtml) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true); // true = es HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Error al enviar correo: " + e.getMessage(), e);
        }
    }

    @Override
    public void enviarEmailConAdjunto(String destinatario, String asunto, String cuerpoHtml, byte[] adjunto, String nombreAdjunto, String contentType) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true); // HTML

            helper.addAttachment(
                    nombreAdjunto,
                    new org.springframework.core.io.ByteArrayResource(adjunto),
                    contentType
            );

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Error al enviar correo con adjunto: " + e.getMessage(), e);
        }
    }

}
