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

@Controller
@SpringBootApplication
public class ControllerApplication {
	@Autowired
	private DbRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ControllerApplication.class, args);
	}

	@GetMapping("/registration")
	public ModelAndView registration(){
		return new ModelAndView("registration");
	}

	@PostMapping("/registration")
	public void postRegistration(@RequestParam String email, @RequestParam String password){
		System.out.println(email + " " + password);
		repository.registerCustomer(email,password);

	}
}


