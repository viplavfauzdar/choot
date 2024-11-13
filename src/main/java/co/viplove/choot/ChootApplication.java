package co.viplove.choot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
//@EnableNeo4jRepositories//(basePackages = {"co.viplove.choot.poc2"})
@EnableNeo4jRepositories //(basePackages = {" co.viplove.choot.repository","co.viplove.choot.poc","co.viplove.choot.poc2", "co.viplove.choot.poc3"})
public class ChootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChootApplication.class, args);
	}

}
