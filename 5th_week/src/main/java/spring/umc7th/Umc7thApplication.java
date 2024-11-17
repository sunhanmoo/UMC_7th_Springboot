package spring.umc7th;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Umc7thApplication {

	public static void main(String[] args) {
		SpringApplication.run(Umc7thApplication.class, args);
	}

}
