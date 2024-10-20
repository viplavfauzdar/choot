package co.viplove.choot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = {"co.viplove.choot.repository"})
public class ChootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChootApplication.class, args);
	}

}
