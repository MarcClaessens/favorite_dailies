package favorite_dailies;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import favorite_dailies.db.H2DatabaseConnection;
import favorite_dailies.db.StickyHist;

@Controller
public class StickyController {
	private String html;
	private long lastFileChange;
	


	@GetMapping("/hist")
	public String showUpdateForm(Model model) {
		List<StickyHist> history = H2DatabaseConnection.getFullHistory();
		model.addAttribute("hist", history);
		return "history";
	}

	@GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String favoriteDailiesHTML() {
		if (html == null || Main.context().favFile().lastModified() != lastFileChange) {
			readFavFile();
		}
		return html;
	}

	private void readFavFile() {
		try {
			System.out.println("Loading " + Main.context().favFile());
			html = new String(Files.readString(Main.context().favFile().toPath()));
			lastFileChange = Main.context().favFile().lastModified();
		} catch (IOException e) {
			throw new IllegalStateException("Could not read file "+ Main.context().favFile(), e);
		}
	}
}
