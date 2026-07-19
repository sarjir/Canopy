package app.canopy.canopy_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

record Board(Long id, String name, Long userId, Instant createdAt) {}

@RestController
@SpringBootApplication
public class CanopyServerApplication {

	private final JdbcTemplate jdbcTemplate;

	public CanopyServerApplication(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/")
	String home() {
		return "Hello World!";
	}

	// Move this to a separate controller class for better organisation
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/boards") 
	// public Board create_board(@RequestBody String name) { // This should include the user too? For now I think I will hardcode it
	public int create_board(@RequestBody String name) { // This should include the user too? For now I think I will hardcode it
		// I think I want to change this to a real ORM in the end.
		int rowsAffected = this.jdbcTemplate.update(
			"INSERT INTO boards (name, user_id) VALUES (?, ?)",
			name,
			1L // Hardcoded user ID for now
		);
		
		// return new Board(1L, name, 1L, Instant.now()); // Respond with JSON representation of the created board
		return rowsAffected;
	}

	public static void main(String[] args) {
		SpringApplication.run(CanopyServerApplication.class, args); // SpringApplication must somehow send in the jdbcTemplate to the constructor of CanopyServerApplication, otherwise it won't work. I think this is done automatically by Spring Boot.
	}

}
