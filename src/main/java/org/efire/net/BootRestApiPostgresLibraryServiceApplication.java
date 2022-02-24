package org.efire.net;

import lombok.extern.slf4j.Slf4j;
import org.efire.net.domain.Author;
import org.efire.net.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BootRestApiPostgresLibraryServiceApplication implements CommandLineRunner {

	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(BootRestApiPostgresLibraryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var author = new Author();
		author.setFirstName("Jong");
		author.setLastName("Tenerife");
		var saveAuthor = authorRepository.save(author);
		log.info(saveAuthor.toString());
	}
}
