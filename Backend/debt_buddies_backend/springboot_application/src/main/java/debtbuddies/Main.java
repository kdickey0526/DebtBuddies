package debtbuddies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {"debtbuddies.GameServer", "debtbuddies.person", "debtbuddies.websocket"})
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
