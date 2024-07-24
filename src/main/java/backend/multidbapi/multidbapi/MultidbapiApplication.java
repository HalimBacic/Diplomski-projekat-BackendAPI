package backend.multidbapi.multidbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "backend.multidbapi.multidbapi")
@EnableJpaRepositories(basePackages = "backend.multidbapi.multidbapi.repository")
@EntityScan(basePackages = "backend.multidbapi.multidbapi.dto")
public class MultidbapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultidbapiApplication.class, args);
	}
}
