package com.example.booking.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender getJavaMailSender() {

        ///Configuracion para el Email server
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        ///Credenciales de la direccion que enviara los correos
        mailSender.setUsername("mfaherold1998@gmail.com");
        //TODO: Configurar passwords en variables en application.properties
        mailSender.setPassword("zlaewloctquluome");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false"); ///Util para diagnostico

        return mailSender;
    }
}

/*
Consideraciones:
1. No exponer contraseñas en el código: En lugar de poner la contraseña directamente en el código, se recomienda utilizar un archivo de configuración seguro como application.properties o variables de entorno.
2. Si utilizas un servicio como Gmail, sería recomendable configurar un password de aplicación para evitar usar tu contraseña principal.
3. En producción, asegúrate de que las propiedades de depuración (mail.debug) estén desactivadas, ya que pueden exponer información sensible en los logs.
4. Si tu aplicación de producción enviará una gran cantidad de correos, considera usar servicios dedicados como SendGrid, Amazon SES o Mailgun, que proporcionan configuraciones más robustas
*/
