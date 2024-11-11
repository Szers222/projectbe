package tdc.edu.vn.project_mobile_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class ProjectMobileBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectMobileBeApplication.class, args);
	}

}
