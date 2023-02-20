package net.javaguides.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.javaguides.sms.repository.PytaniaRepository;

@SpringBootApplication
public class PytaniaManagementSystemApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(PytaniaManagementSystemApplication.class, args);
	}

	@Autowired
	private PytaniaRepository pytaniaRepository;

	@Override
	public void run(String... args) throws Exception {



	}

}
