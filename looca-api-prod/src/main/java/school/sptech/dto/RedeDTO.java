package school.sptech.dto;

import java.time.LocalDateTime;

public class RedeDTO {

    private Long idRede;
    private Long idMaquina;
    private Long idComponente;
    private Double download;
    private Double upload;
    private Double packetLoss;
    private LocalDateTime dthCaptura;

    public RedeDTO(Long idRede, Long idMaquina, Long idComponente, Double download, Double upload, Double packetLoss, LocalDateTime dthCaptura) {
        this.idRede = idRede;
        this.idMaquina = idMaquina;
        this.idComponente = idComponente;
        this.download = download;
        this.upload = upload;
        this.packetLoss = packetLoss;
        this.dthCaptura = dthCaptura;
    }

    public RedeDTO(long idRede, long idMaquina, long idComponente, double download, double upload, double packetLoss, Object dthCaptura, Class<LocalDateTime> localDateTimeClass) {
    }

    public Long getIdRede() {
        return idRede;
    }

    public Long getIdMaquina() {
        return idMaquina;
    }

    public Long getIdComponente() {
        return idComponente;
    }

    public Double getDownload() {
        return download;
    }

    public Double getUpload() {
        return upload;
    }

    public Double getPacketLoss() {
        return packetLoss;
    }

    public LocalDateTime getDthCaptura() {
        return dthCaptura;
    }
}
