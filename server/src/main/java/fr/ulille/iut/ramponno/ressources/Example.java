package fr.ulille.iut.ramponno.ressources;

import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import com.sendgrid.*;
import java.io.IOException;

public class Example {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom()
                .useSystemProperties()
                .setProxy(new HttpHost("cache.univ-lille.fr", 3128))
                .build();

        Client client = new Client(httpclient);

        Email from = new Email("dacruzaxel21@gmail.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("dacruzaxel21@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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", client);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}



/*public class Example {
    /*public static void main(String[] args) throws Exception {
        String URL = "https://api.sendgrid.com/v3/mail/send";
        String SENDGRID_API_KEY = "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";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL);

        System.out.println("Apres le crearte builder");

        post.setHeader("Authorization", "Bearer " + SENDGRID_API_KEY);
        post.setHeader("Content-Type", "application/json");


        System.out.println("Apres le post du header");

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("personalizations", "[{\"to\": [{\"email\": \"dacruzaxel21@gmail.com\"}]}]"));
        urlParameters.add(new BasicNameValuePair("from", "{\"email\": \"dacruzaxel21@gmail.com\"}"));
        urlParameters.add(new BasicNameValuePair("subject", "Hello, world"));
        urlParameters.add(new BasicNameValuePair("content", "[{\"type\": \"text/plain\", \"value\": \"Hello world\"}]"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        System.out.println("Apres le post des parametres");

        HttpResponse response = client.execute(post);

        System.out.println("Apres l'execute du post");

        System.out.println("Response Code : "+ response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
    }*/

    /*public static void main(String[] args) throws IOException, InterruptedException {
        String commande = "curl -X POST --url https://api.sendgrid.com/v3/mail/send -H 'Authorization: Bearer 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' -H 'Content-Type: application/json' --data '{\"personalizations\": [{\"to\": [{\"email\": \"dacruzaxel21@gmail.com\"}]}],\"from\": {\"email\": \"dacruzaxel21@gmail.com\"},\"subject\": \"Sending with SendGrid is Fun\",\"content\": [{\"type\": \"text/plain\", \"value\": \"and easy to do anywhere, even with cURL\"}]}'";

        /*Process process = Runtime.getRuntime().exec(commande);
        System.out.println(commande);
        InputStream inputStream = process.getInputStream();
        process.destroy();*

        //String cmd = "ls -al";

        Runtime run = Runtime.getRuntime();

        Process process = run.exec(commande);

        process.waitFor();

        BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = "";

        while ((line=buf.readLine())!=null) {

            System.out.println(line);

        }
    }*
}*/