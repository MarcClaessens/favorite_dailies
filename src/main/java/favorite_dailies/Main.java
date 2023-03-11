package favorite_dailies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		H2DatabaseConnection.initDb();
		SpringApplication.run(Main.class, args);
	}
}
