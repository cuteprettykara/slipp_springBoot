package net.slipp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SlippSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlippSpringBootApplication.class, args);
	}
}
