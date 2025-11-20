package school.sptech.entity.connectBD;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import io.github.cdimascio.dotenv.Dotenv;

public class DBConnectionProvider {

    private final JdbcTemplate jdbcTemplate;
    private final BasicDataSource basicDataSource;

    public DBConnectionProvider() {
        BasicDataSource basicDataSource = new BasicDataSource();
        Dotenv dotenv = Dotenv.load();
        String ip = dotenv.get("IP_BANCO");
        String user = dotenv.get("USUARIO");
        String password = dotenv.get("PASSWORD");
        basicDataSource.setUrl("jdbc:mysql://" + ip + ":3307/cyberbeef?useSSL=false&serverTimezone=UTC");
        basicDataSource.setUsername(user);  // seu usuário do MySQL
        basicDataSource.setPassword(password);  // sua senha do MySQL
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        this.basicDataSource = basicDataSource;
        this.jdbcTemplate = new JdbcTemplate(basicDataSource);
    }

    public BasicDataSource getBasicDataSource() {
        return basicDataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
