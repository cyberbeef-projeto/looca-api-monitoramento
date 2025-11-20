package school.sptech.dto;

import java.time.LocalDateTime;

public class LogDTO {

    private Long idLog;
    private Long idMaquina;
    private String tipo;
    private String mensagem;
    private LocalDateTime criadoEm;

    public LogDTO(Long idLog, Long idMaquina, String tipo, String mensagem, LocalDateTime criadoEm) {
        this.idLog = idLog;
        this.idMaquina = idMaquina;
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.criadoEm = criadoEm;
    }

    // Getters e Setters
    public Long getIdLog() {
        return idLog;
    }

    public void setIdLog(Long idLog) {
        this.idLog = idLog;
    }

    public Long getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Long idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
