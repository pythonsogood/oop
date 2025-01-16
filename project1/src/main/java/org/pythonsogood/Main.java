package org.pythonsogood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;


@SpringBootApplication
@RestController
public class Main {
	private Similarity similarity = new Similarity();

    public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
    }

	@PostMapping("/similarity")
	public String similarity(@RequestParam(value = "s1", required = true) String s1, @RequestParam(value = "s2", required = true) String s2) {
		double score = this.similarity.similarity(s1, s2);
		return String.format("{\"score\": %f}", score);
	}
}