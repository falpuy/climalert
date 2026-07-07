package ar.utn.ba.ddsi.mailing.models.entities;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class Email {
    private Long id;
    private String destinatario;
    private String remitente;
    private String asunto;
    private String contenido;
    private boolean enviado;

    public Email(String destinatario, String remitente, String asunto, String contenido) {
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.asunto = asunto;
        this.contenido = contenido;
        this.enviado = false;
    }

    public String enviar() {
        JavaMailSenderImpl mensajero = new JavaMailSenderImpl();
        mensajero.setHost("smtp.gmail.com");
        mensajero.setPort(587);
        mensajero.setUsername("falpuy@frba.utn.edu.ar");
        mensajero.setPassword("phjcbbipjjzsbhki");

        MimeMessage  mensaje = mensajero.createMimeMessage();
        try {
        MimeMessageHelper ayudante = new MimeMessageHelper(mensaje,true);
        ayudante.setFrom(this.remitente);
        ayudante.setTo(this.destinatario);
        ayudante.setText(this.contenido);
        ayudante.setSubject(this.asunto);
        mensajero.send(mensaje);
        System.out.println("llegue aca, no se como");
        return "Mail con asunto: "+this.asunto+" enviado exitosamente desde "+this.remitente+" a "+this.destinatario+" a las "+LocalDateTime.now();
        }
        catch(MessagingException e) {
            return "Algo salio mal";
        }
    }
} 