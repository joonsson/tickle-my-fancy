package se.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.academy.Domain.Customer;
import se.academy.repository.DbRepository;
import javax.servlet.http.HttpSession;

@Controller
public class FancyController {
    @Autowired
    private DbRepository repository;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        return "index";
    }

    @GetMapping("/login")
    public String logintest(HttpSession session){
        //TODO move login to indexpage?
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, HttpSession session, @RequestParam String email, @RequestParam String password) {
        Customer customer = repository.loginCustomer(email,password);
        if (customer == null){
            model.addAttribute("errorString","Email or Password does not exist");
            return "login";
        }
        else{
            session.setAttribute("sessionCustomer",customer);
            return "index"; //TODO make it return page you were on
        }
    }

    @GetMapping("/search")
    public String search(Model model, HttpSession session, @RequestParam String srch) {

        return "index"; //TODO make it search results
    }

    @GetMapping("/p")
    public String product(Model model, HttpSession session, @RequestParam int id) {

        return "index"; //TODO make it product with ID id
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {

        return "index"; //TODO make it return page you were on
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        return new ModelAndView("registration");
    }

    @PostMapping("/registration")
    public void postRegistration(@RequestParam String email, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String address, @RequestParam String zip, @RequestParam String city, @RequestParam String phone){
        System.out.println(email + " " + password);
        repository.registerCustomer(email,password, firstName, lastName, address, zip, city, phone);
    }

}
