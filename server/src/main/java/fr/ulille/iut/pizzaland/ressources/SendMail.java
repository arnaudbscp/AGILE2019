package fr.ulille.iut.pizzaland.ressources;

import com.sendgrid.*;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class SendMail {
    private Email from;
    private String subject;
    private Email to;
    private Content content;
    private final static String SENDGRID_API_KEY = "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";
    
    public SendMail(Email from, String subject, Email to, Content content) {
        this.from = from;
        this.subject = subject;
        this.to = to;
        this.content = content;
    }

    public SendMail(){}

    public Email getFrom() {
        return from;
    }

    public void setFrom(Email from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Email getTo() {
        return to;
    }

    public void setTo(Email to) {
        this.to = to;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String toString() {
        return   "From: " + this.from + "\n"
               + "Subject: " + this.subject + "\n"
               + "To: " + this.to +"\n"
               + "Content: " + this.content.toString();
    }

    public boolean sendMail(String message) {
        CloseableHttpClient httpclient = HttpClients.custom()
                .useSystemProperties()
                .setProxy(new HttpHost("cache.univ-lille.fr", 3128))
                .build();

        Client client = new Client(httpclient);
        this.content = new Content("text/plain", message);
        Mail mail = new Mail(this.from, this.subject, this.to, this.content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY, client);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
