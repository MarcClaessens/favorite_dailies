package favorite_dailies;

import java.io.File;
import java.nio.file.Files;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StickyController implements InitializingBean {
	private String html;
	
	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String favoriteDailiesHTML() {
		return html;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		String homedir = System.getProperty("user.home");
		File favFile = new File(homedir, "Documents/favorite_comics.html");
		if (!favFile.exists()) {
			throw new IllegalStateException(favFile + " does not exist");
		}
		html = new String(Files.readString(favFile.toPath()));		
	}
}
