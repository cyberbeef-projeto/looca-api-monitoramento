package school.sptech.entity;

import java.time.LocalDateTime;

public class Rede {
    private Long idRede;
    private Long idMaquina;
    private Long idComponente;
    private Double download;
    private Double upload;
    private Double packetLoss;
    private LocalDateTime dthCaptura;

    public Rede() {}

    public Rede(Long idRede, Long idMaquina, Long idComponente, Double download, Double upload, Double packetLoss, LocalDateTime dthCaptura) {
        this.idRede = idRede;
        this.idMaquina = idMaquina;
        this.idComponente = idComponente;
        this.download = download;
        this.upload = upload;
        this.packetLoss = packetLoss;
        this.dthCaptura = dthCaptura;
    }

    public Long getIdRede() { return idRede; }
    public void setIdRede(Long idRede) { this.idRede = idRede; }

    public Long getIdMaquina() { return idMaquina; }
    public void setIdMaquina(Long idMaquina) { this.idMaquina = idMaquina; }

    public Long getIdComponente() { return idComponente; }
    public void setIdComponente(Long idComponente) { this.idComponente = idComponente; }

    public Double getDownload() { return download; }
    public void setDownload(Double download) { this.download = download; }

    public Double getUpload() { return upload; }
    public void setUpload(Double upload) { this.upload = upload; }

    public Double getPacketLoss() { return packetLoss; }
    public void setPacketLoss(Double packetLoss) { this.packetLoss = packetLoss; }

    public LocalDateTime getDthCaptura() { return dthCaptura; }
    public void setDthCaptura(LocalDateTime dthCaptura) { this.dthCaptura = dthCaptura; }
}
