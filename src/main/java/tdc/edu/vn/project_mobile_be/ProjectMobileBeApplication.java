package tdc.edu.vn.project_mobile_be;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tdc.edu.vn.project_mobile_be.interfaces.service.DatabaseTriggerService;

@SpringBootApplication
public class ProjectMobileBeApplication implements CommandLineRunner {
	@Autowired
	private DatabaseTriggerService databaseTriggerService;

	public static void main(String[] args) {
		SpringApplication.run(ProjectMobileBeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		databaseTriggerService.createTriggerAndFunction();
	}
}
