package school.sptech.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public class SlackRepository {

    private final JdbcTemplate jdbc;

    public SlackRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void salvarLogSlack(Long idMaquina, String tipo, String mensagem) {
        String sql = """
            INSERT INTO log (id_maquina, tipo, mensagem, criado_em)
            VALUES (?, ?, ?, NOW())
        """;
        jdbc.update(sql, idMaquina, tipo, mensagem);
    }

    public void salvarAlerta(Long idMaquina,
                             Long idComponente,
                             Long idParametro,
                             String descricao) {

        String sql = """
            INSERT INTO alerta (idLeitura, idComponente, idMaquina, idParametro, descricao)
            VALUES (NULL, ?, ?, ?, ?)
        """;

        jdbc.update(sql, idComponente, idMaquina, idParametro, descricao);
    }
}
