package ibf2021.assess2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

@SpringBootApplication(
	exclude = { RedisRepositoriesAutoConfiguration.class }
)
public class Assess2Application {

	public static void main(String[] args) {
		SpringApplication.run(Assess2Application.class, args);
	}

}
