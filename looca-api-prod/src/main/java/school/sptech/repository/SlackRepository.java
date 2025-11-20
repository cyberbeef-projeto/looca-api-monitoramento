package school.sptech.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import school.sptech.dto.LogDTO;

import java.util.List;

public class SlackRepository {

    private final JdbcTemplate jdbcTemplate;

    public SlackRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Corrigir o nome do campo idLog no banco de dados, caso seja necessário
    public void salvarLogSlack(Long idMaquina, String tipo, String mensagem) {
        String sql = """
            INSERT INTO log (id_maquina, tipo, mensagem, criado_em)
            VALUES (?, ?, ?, NOW())
            """;
        jdbcTemplate.update(sql, idMaquina, tipo, mensagem);
    }

    public List<LogDTO> buscarLogsSlack() {
        String sql = """
            SELECT idLog, id_maquina, tipo, mensagem, criado_em 
            FROM log 
            ORDER BY criado_em DESC
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LogDTO(
                rs.getLong("idLog"),  // Ajustar o nome do campo de idLog conforme o seu banco
                rs.getLong("id_maquina"),
                rs.getString("tipo"),
                rs.getString("mensagem"),
                rs.getTimestamp("criado_em").toLocalDateTime()
        ));
    }

    public List<LogDTO> buscarLogsPorTipo(String tipo) {
        String sql = """
            SELECT idLog, id_maquina, tipo, mensagem, criado_em 
            FROM log 
            WHERE tipo = ? 
            ORDER BY criado_em DESC
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LogDTO(
                rs.getLong("idLog"),
                rs.getLong("id_maquina"),
                rs.getString("tipo"),
                rs.getString("mensagem"),
                rs.getTimestamp("criado_em").toLocalDateTime()
        ), tipo);
    }

    public List<LogDTO> buscarLogsPorIdMaquina(Long idMaquina) {
        String sql = """
            SELECT idLog, id_maquina, tipo, mensagem, criado_em 
            FROM log 
            WHERE id_maquina = ? 
            ORDER BY criado_em DESC
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new LogDTO(
                rs.getLong("idLog"),
                rs.getLong("id_maquina"),
                rs.getString("tipo"),
                rs.getString("mensagem"),
                rs.getTimestamp("criado_em").toLocalDateTime()
        ), idMaquina);
    }
}
