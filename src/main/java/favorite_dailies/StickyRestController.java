package favorite_dailies;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import favorite_dailies.db.H2DatabaseConnection;
import favorite_dailies.db.StickyHist;

@RestController
public class StickyRestController {
	
	@GetMapping("/sticky")
	public StickyHist get() {
		return H2DatabaseConnection.getLatestStickText();
	}
	
	@PostMapping("/sticky") 
	public void set(@RequestBody String sticky){
		StickyHist latest = H2DatabaseConnection.getLatestStickText();
		if (!latest.getStickyText().equals(sticky)) {
			H2DatabaseConnection.insertStickyData(sticky);
			System.out.println(String.format("Database entry added.  New total is %d entries", H2DatabaseConnection.getEntries()));

		}
	}
	
	@DeleteMapping("/sticky/{id}") 
	public void delete(@PathVariable("id") Long id, Model model){
		if (id != null) {
			H2DatabaseConnection.deleteHistory(id);
		}
	}
}
