package hrms.human_resource_system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication(scanBasePackages = "hrms.human_resource_system")
@ConfigurationPropertiesScan("hrms.human_resource_system")
public class HumanResourceSystemApplication {

	public static void main(String[] args) throws UnknownHostException {
		// Set default timezone to UTC (best practice for server apps)
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		// Start the Spring Boot app and get Environment
		Environment env = SpringApplication.run(HumanResourceSystemApplication.class, args).getEnvironment();

		// Pretty print startup info
		log.info("""
                ----------------------------------------------------------
                HumanResourceSystem Application is running! Access URLs:
                Local:     http://127.0.0.1:{}
                External:  http://{}:{}
                Profile(s): {}
                ----------------------------------------------------------
                """,
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				env.getActiveProfiles().length == 0 ? "default" : String.join(", ", env.getActiveProfiles())
		);
	}
}
