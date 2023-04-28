package pl.projekt_symulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController  {


    @Autowired
   private JavaMailSender mailSender;

    @GetMapping("/mail")
    @ResponseBody
    public String sendEmail() {


        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("strzelnicanozyno@gmail.com");
        message.setTo("Mateuszkonkel132@gmail.com");
        message.setSubject("Symulator strzelecki nożyno - rejestracja");
        message.setText("Cześć W celu potwierdzenia rejestracji kliknij w poniższy link");

        mailSender.send(message);
        return "Udało się ";
    }
}