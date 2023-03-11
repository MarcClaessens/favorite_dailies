package favorite_dailies;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		String homedir = System.getProperty("user.home");
		File f = new File(homedir, "Documents/favorite_comics.html");
		if (!f.exists()) {
			throw new IllegalStateException(f + " does not exist");
		}
		H2DatabaseConnection.initDb();
		SpringApplication.run(Main.class, args);
	}
}
