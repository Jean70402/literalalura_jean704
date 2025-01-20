package com.levelblock.literalalura;

import com.levelblock.literalalura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteralaluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteralaluraApplication.class, args);
	}
	private final Principal principal;

	@Autowired
	public LiteralaluraApplication(Principal principal) {
		this.principal = principal;
	}

	@Override
	public void run(String... args) throws Exception {
		principal.muestraElMenu();
	}

}
