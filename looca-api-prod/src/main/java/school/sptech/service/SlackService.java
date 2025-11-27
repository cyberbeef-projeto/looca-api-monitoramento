package school.sptech.service;

import school.sptech.repository.SlackRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SlackService {

    private final SlackRepository repository;
    private final String webhookUrl;

    public SlackService(SlackRepository slackRepository) {
        Dotenv dotenv = Dotenv.load();
        this.webhookUrl = dotenv.get("URL_WEBHOOK");  // precisa ser um webhook válido
        this.repository = slackRepository;
    }

    public void enviarMensagemSlack(Long idMaquina,
                                    Long idComponente,
                                    Long idParametro,
                                    String tipo,
                                    String mensagemOriginal) {

        // MONTA A DESCRIÇÃO formatada como você pediu
        String descricaoFormatada = tipo + " - " + mensagemOriginal;

        // 1) Salva log SEMPRE
        repository.salvarLogSlack(idMaquina, tipo, descricaoFormatada);

        // 2) Só gera alerta e envia para Slack se ANORMAL ou CRITICO
        if (tipo.equalsIgnoreCase("ANORMAL") || tipo.equalsIgnoreCase("CRITICO")) {

            // Salvar alerta com a descrição formatada
            repository.salvarAlerta(idMaquina, idComponente, idParametro, descricaoFormatada);

            // Enviar para Slack
            enviarParaSlack(descricaoFormatada);
        }
    }

    private void enviarParaSlack(String mensagem) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String json = String.format("{\"text\": \"%s\"}", mensagem.replace("\"", "'"));

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            System.out.println("Slack response: " + conn.getResponseCode());
        }
        catch (Exception e) {
            System.err.println("Erro ao enviar mensagem ao Slack: " + e.getMessage());
        }
    }
}
