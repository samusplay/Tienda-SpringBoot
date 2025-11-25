package com.example.tienda.service;

public interface EmailService {
    /**
     * Enviar un correo simple (solo texto o HTML sin adjuntos)
     */
    void enviarEmail(
            String destinatario,
            String asunto,
            String cuerpoHtml
    );

    /**
     * Enviar un correo con un archivo adjunto (PDF, Excel, etc.)
     */
    void enviarEmailConAdjunto(
            String destinatario,
            String asunto,
            String cuerpoHtml,
            byte[] adjunto,
            String nombreAdjunto,
            String contentType // ej: "application/pdf"
    );
}
