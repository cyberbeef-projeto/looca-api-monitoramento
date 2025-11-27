package school.sptech.service;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import school.sptech.repository.RedeRepository;
import school.sptech.dto.RedeDTO;

import java.util.List;

public class RedeService {

    private final RedeRepository repository;
    private final SlackService slackService;

    public RedeService(RedeRepository repository, SlackService slackService) {
        this.repository = repository;
        this.slackService = slackService;
    }

    public void monitorarRede() throws InterruptedException {

        Looca looca = new Looca();
        List<RedeInterface> interfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();

        Long idMaquina = 1L;
        Long idComponente = repository.buscarIdComponenteRede(idMaquina);

        if (idComponente == null) {
            System.err.println("Nenhum componente do tipo 'REDE' encontrado para a máquina ID " + idMaquina);
            return;
        }

        System.out.println("Monitorando rede para Máquina ID " + idMaquina + " | Componente ID " + idComponente);

        while (true) {

            Long bytesRecebidosAntes = getBytesRecebidos(interfaces);
            Long bytesEnviadosAntes = getBytesEnviados(interfaces);
            Long pacotesRecebidosAntes = getPacotesRecebidos(interfaces);
            Long pacotesEnviadosAntes = getPacotesEnviados(interfaces);

            Thread.sleep(5000);

            Long bytesRecebidosDepois = getBytesRecebidos(interfaces);
            Long bytesEnviadosDepois = getBytesEnviados(interfaces);
            Long pacotesRecebidosDepois = getPacotesRecebidos(interfaces);
            Long pacotesEnviadosDepois = getPacotesEnviados(interfaces);

            double downloadMbps = (bytesRecebidosDepois - bytesRecebidosAntes) * 8.0 / 1_000_000.0;
            double uploadMbps = (bytesEnviadosDepois - bytesEnviadosAntes) * 8.0 / 1_000_000.0;

            double difRecv = pacotesRecebidosDepois - pacotesRecebidosAntes;
            double difSend = pacotesEnviadosDepois - pacotesEnviadosAntes;

            double packetLoss = 0.0;
            if (difSend > 0) {
                packetLoss = (difSend - difRecv) / difSend * 100.0;
                packetLoss = Math.max(0.0, Math.min(packetLoss, 100.0));
            }

            // salva no banco
            repository.salvarDadosRede(idMaquina, idComponente, downloadMbps, uploadMbps, packetLoss);

            String tipoLog = null;
            if (packetLoss > 10) {
                tipoLog = "Crítico";
            } else if (packetLoss >= 2.5) {
                tipoLog = "Anormal";
            }

            if (tipoLog != null) {
                String mensagemLog = String.format(
                        "ALERTA DE REDE | Máquina %d | Download: %.2f Mbps | Upload: %.2f Mbps | Packet Loss: %.2f%%",
                        idMaquina, downloadMbps, uploadMbps, packetLoss
                );

                slackService.enviarMensagemSlack(
                        idMaquina,
                        idComponente,
                        4L,  // idParametro
                        tipoLog,
                        mensagemLog
                );
            }

            mostrarPainel(downloadMbps, uploadMbps, packetLoss);
            mostrarLeituras();
        }
    }

    private void mostrarPainel(double download, double upload, double packetLoss) {
        System.out.println("\n========== MONITORAMENTO DE REDE ==========");
        System.out.printf("Download: %.2f Mbps%n", download);
        System.out.printf("Upload: %.2f Mbps%n", upload);
        System.out.printf("Packet Loss: %.2f %%\n", packetLoss);
        System.out.println("-------------------------------------------");
    }

    private void mostrarLeituras() {
        List<RedeDTO> leituras = repository.buscarDadosRede();
        System.out.printf("%-10s %-10s %-12s %-12s %-12s %-20s%n",
                "ID", "COMPONENTE", "DOWNLOAD", "UPLOAD", "LOSS", "DATA");

        for (RedeDTO l : leituras) {
            System.out.printf("%-10d %-10d %-12.2f %-12.2f %-12.2f %-20s%n",
                    l.getIdRede(), l.getIdComponente(), l.getDownload(),
                    l.getUpload(), l.getPacketLoss(), l.getDthCaptura());
        }
    }

    private Long getBytesRecebidos(List<RedeInterface> interfaces) {
        return interfaces.stream().map(RedeInterface::getBytesRecebidos).reduce(0L, Long::sum);
    }

    private Long getBytesEnviados(List<RedeInterface> interfaces) {
        return interfaces.stream().map(RedeInterface::getBytesEnviados).reduce(0L, Long::sum);
    }

    private Long getPacotesRecebidos(List<RedeInterface> interfaces) {
        return interfaces.stream().map(RedeInterface::getPacotesRecebidos).reduce(0L, Long::sum);
    }

    private Long getPacotesEnviados(List<RedeInterface> interfaces) {
        return interfaces.stream().map(RedeInterface::getPacotesEnviados).reduce(0L, Long::sum);
    }
}
