package spring.umc7th;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import spring.umc7th.service.storeService.StoreQueryService;

@SpringBootApplication
@EnableJpaAuditing
public class Umc7thApplication {

	public static void main(String[] args) {
		SpringApplication.run(Umc7thApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ApplicationContext context) {
		return args -> {
			StoreQueryService storeQueryService = context.getBean(StoreQueryService.class);

			// 파라미터 값 설정
			String name = "요아정";
			Float score = 4.0f;

			// 쿼리 메서드 호출 및 쿼리 문자열과 파라미터 출력
			System.out.println("Executing findStoresByNameAndScore with parameters:");
			System.out.println("Name: " + name);
			System.out.println("Score: " + score);

			storeQueryService.findStoresByNameAnsScore(name, score)
					.forEach(System.out::println);
		};
	}
}
