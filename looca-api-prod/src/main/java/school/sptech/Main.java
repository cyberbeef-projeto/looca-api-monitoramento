package school.sptech;

import school.sptech.entity.connectBD.DBConnectionProvider;
import school.sptech.repository.RedeRepository;
import school.sptech.repository.SlackRepository;
import school.sptech.service.RedeService;
import school.sptech.service.SlackService;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        DBConnectionProvider db = new DBConnectionProvider();
        RedeRepository repository = new RedeRepository(db.getJdbcTemplate());
        SlackRepository slack = new SlackRepository(db.getJdbcTemplate());

        SlackService slackService = new SlackService(slack);
        RedeService redeService = new RedeService(repository, slackService);
        redeService.monitorarRede();
        }
    }
