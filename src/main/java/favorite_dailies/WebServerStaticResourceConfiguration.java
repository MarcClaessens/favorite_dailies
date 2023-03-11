package favorite_dailies;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebServerStaticResourceConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
		String homedir = System.getProperty("user.home");
		
		File f = new File(homedir, "Documents/favorite_comics.html");
		if (! f.exists()) {
			throw new IllegalStateException(f + " does not exist");
		}
		try {
			URL url = f.toURI().toURL();
			System.out.println("Default forward to " + url);
			registry.addViewController("/").setViewName("forward:" + url);
		} catch (MalformedURLException mue) {
			throw new IllegalStateException("Could not load html " + f, mue);
		}
    }
}
