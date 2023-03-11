package favorite_dailies;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StickyRestController {
	
	@GetMapping("/sticky")
	public String get() {
		return H2DatabaseConnection.getLatestStickText();
	}
	
	@PostMapping("/sticky") 
	public void set(@RequestBody String sticky){
		String latest = H2DatabaseConnection.getLatestStickText();
		if (!latest.equals(sticky)) {
			H2DatabaseConnection.insertStickyData(sticky);
			System.out.println(String.format("Database entry added.  New total is %d entries", H2DatabaseConnection.getEntries()));

		}
	}
}
