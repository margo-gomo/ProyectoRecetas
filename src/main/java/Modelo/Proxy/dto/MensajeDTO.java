package Modelo.Proxy.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MensajeDTO {
    private Long id;
    private String remitente;
    private String destinatario;
    private String texto;
    private LocalDateTime fechaEnvio;
}
