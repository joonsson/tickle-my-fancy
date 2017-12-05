package se.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import se.academy.repository.DbRepository;

@SpringBootApplication
public class ControllerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ControllerApplication.class, args);
	}


}


