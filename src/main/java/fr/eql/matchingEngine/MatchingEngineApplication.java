package fr.eql.matchingEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//@PropertySource(value = "file:c:/afcepf/matchingEngine/src/main/resources/application.yml")
@SpringBootApplication
public class MatchingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchingEngineApplication.class, args);
	}

}
