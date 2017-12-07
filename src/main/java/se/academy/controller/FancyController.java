package se.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.academy.domain.Customer;
import se.academy.repository.DbRepository;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class FancyController {
    @Autowired
    private DbRepository repository;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {

        model.addAttribute("makeUp", repository.getBySubCategoryTop3("Fransar"));
        model.addAttribute("nails", repository.getBySubCategoryTop3("läppstift"));
        model.addAttribute("eyes", repository.getBySubCategoryTop3("Fransar"));
        
        boolean isLogedIn;
        if(session.getAttribute("sessionCustomer") == null){
            isLogedIn = false;
        }else{
            isLogedIn = true;
        }
        if(session.getAttribute("loginFail") != null){
            model.addAttribute("loginFail",session.getAttribute("loginFail"));
        }
        model.addAttribute("isLogedIn",isLogedIn);
        return "index";
    }

    @RequestMapping("/customerpage")
    public String showPersonalPage(Model model, HttpSession session){
        Customer customer = (Customer) session.getAttribute("sessionCustomer");
        if(customer == null){
            return "redirect:/";
        }
        model.addAttribute("customer",customer);
        return "customerpage";
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
            String loginFail = "Inloggning misslyckades";
            session.setAttribute("loginFail",loginFail);
            return "redirect:/";
        }
        else{
            session.setAttribute("sessionCustomer",customer);
            session.removeAttribute("loginFail");
            return "redirect:/"; //TODO make it return page you were on
        }
    }

    @GetMapping("/search")
    public String search(Model model, HttpSession session, @RequestParam String srch) {
        model.addAttribute("products", repository.search(srch));
        return "search";
    }

    @GetMapping("/p")
    public String product(Model model, HttpSession session, @RequestParam int id) {

        return "index"; //TODO make it product with ID id
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {

        return "redirect:/"; //TODO make it return page you were on
    }

    @PostMapping("/logout")
    public String logoutPost(HttpSession session){
        //TODO cookies and stuff
        session.removeAttribute("sessionCustomer");
        return "redirect:/";
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        Customer customer = new Customer();
        return new ModelAndView("registration").addObject("customer", customer);
    }

    @PostMapping("/registration")
    public ModelAndView registration(@Valid Customer customer, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()) {
            return new ModelAndView("registration").addObject("customer", customer);
        }
        if(repository.checkIfCustomerExist(customer)) {
            String emailOccupied = "Email redan registrerat";
            return  new ModelAndView("registration").addObject("customer", customer).addObject("occupied",emailOccupied);
        }
        repository.registerCustomer(customer);
        return new ModelAndView("index");
    }

    // HAR JAG TÄNKT RÄTT HÄR?,
    @GetMapping("/productinfo")
    public String productInfo (Model model, HttpSession session, @RequestParam int productID){
        model.addAttribute("product", repository.getProduct(productID));

        return "productinfo";
    }
}
