package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IEmailRepository;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService implements IEmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final IEmailRepository emailRepository;

    public EmailService(IEmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public Email crearEmail(Email email) {
        return emailRepository.save(email);
    }

    @Override
    public List<Email> obtenerEmails(Boolean pendiente) {
        if (pendiente != null) {
            return emailRepository.findByEnviado(!pendiente);
        }
        return emailRepository.findAll();
    }

    @Override
    public List<String> procesarPendientes() {
        List<Email> pendientes = emailRepository.findByEnviado(false);
        List<String> respuestas = new ArrayList<>();
        for (Email email : pendientes) {
            String respuesta = email.enviar();
            email.setEnviado(true);
            emailRepository.save(email);
            respuestas.add(respuesta);
        }
        return respuestas;
    }

    @Override
    public void loguearEmailsPendientes() {
        List<Email> pendientes = obtenerEmails(true);
        logger.info("Emails pendientes de envío: {}", pendientes.size());
        pendientes.forEach(email -> 
            logger.info("Email pendiente - ID: {}, Destinatario: {}, Asunto: {}", 
                email.getId(),
                email.getDestinatario(), 
                email.getAsunto())
        );
    }
} 