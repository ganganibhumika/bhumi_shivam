
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(scanBasePackages = "com.techhive")
@EnableScheduling
@EnableCaching
public class ShivamwebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ShivamwebApplication.class);
	}

	@RequestMapping(value = "/")
	public String testWeb() {
		return "Hello from Shivam WEB";
	}

	public static void main(String[] args) {
		SpringApplication.run(ShivamwebApplication.class, args);
//		Logger l = LoggerFactory.getLogger(ShivamwebApplication.class);
//		l.warn("catalina.home::"+ System.getProperty("catalina.home"));
	}
}
