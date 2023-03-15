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
	
	public interface AppContext {
		File getFavFile();
		String getH2File();
	}
	
	private class AppContextImpl implements AppContext {
		private final File favFile;
		private final String h2File;
		protected AppContextImpl (File favFile, String h2File) {
			this.favFile = favFile;
			this.h2File = h2File;
		}
		
		public File getFavFile() {
			return favFile;
		}
		
		public String getH2File() {
			return h2File;
		}
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
		String h2Location = favDir + (favDir.endsWith("/")? "": "/") + "favorite_dailies.h2db";
		INSTANCE = new AppContextImpl(favFile, h2Location);
		
	}
	
	public static AppContext context() {
		return INSTANCE;
	}
}
