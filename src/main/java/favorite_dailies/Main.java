package favorite_dailies;

import java.io.File;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements InitializingBean {
	private static AppContext INSTANCE;
	
	@Value("${fav.dir}")
	private String favDir;
	@Value("${db.user}")
	private String dbUser;
	@Value("${db.pass}")
	private String dbCred;
	
	public record AppContext(File favFile, String h2DbFile, String h2SqlFile, String dbUser, String dbCred) {
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@Override
	public void afterPropertiesSet() {
		File favFile = new File(favDir, "favorite_comics.html");
		if (!favFile.exists()) {
			throw new IllegalStateException(favFile + " does not exist.  Configure fav.dir in application.properties");
		}
		String h2DbLocation = favDir + (favDir.endsWith("/")? "": "/") + "favorite_dailies.h2db";
		String h2SqlLocation = favDir + (favDir.endsWith("/")? "": "/") + "favorite_dailies.sql";
		INSTANCE = new AppContext(favFile, h2DbLocation, h2SqlLocation, dbUser, dbCred);
	}
	
	public static AppContext context() {
		return INSTANCE;
	}
}
