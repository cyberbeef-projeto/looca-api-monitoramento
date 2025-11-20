package school.sptech.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;
import school.sptech.repository.SlackRepository;

import java.io.IOException;

public class SlackService {

    private final String webhookUrl;
    private final SlackRepository slackRepository;

    // Construtor atualizado para aceitar o SlackRepository
    public SlackService(SlackRepository slackRepository) {
        Dotenv dotenv = Dotenv.load();
        this.webhookUrl = dotenv.get("URL_WEBHOOK");  // Carrega o SLACK_WEBHOOK_URL do .env
        this.slackRepository = slackRepository;
    }

    // Envia mensagem para o Slack e salva log
    public void enviarMensagemSlack(Long idMaquina, String tipo, String mensagem) {
        String texto = String.format("Tipo: %s\nMáquina ID: %d\nMensagem: %s", tipo, idMaquina, mensagem);

        // Configura o payload JSON para enviar ao Slack
        String jsonPayload = "{ \"text\": \"" + texto + "\" }";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(webhookUrl);
            StringEntity entity = new StringEntity(jsonPayload);
            post.setEntity(entity);
            post.setHeader("Content-type", "application/json");

            // Executa a requisição HTTP
            try (org.apache.http.client.methods.CloseableHttpResponse response = client.execute(post)) {
                HttpEntity responseEntity = response.getEntity();
                String responseString = EntityUtils.toString(responseEntity);
                System.out.println("Mensagem enviada para o Slack: " + responseString);
            }

            // Registra o log no banco de dados
            slackRepository.salvarLogSlack(idMaquina, tipo, mensagem);

        } catch (IOException e) {
            System.err.println("Erro ao enviar mensagem para o Slack: " + e.getMessage());
        }
    }
}
