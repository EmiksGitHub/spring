package com.course.autopodborplatform.controllers;

import com.course.autopodborplatform.models.Company;
import com.course.autopodborplatform.repositories.CompanyRepository;
import com.course.autopodborplatform.repositories.RoleRepository;
import com.course.autopodborplatform.repositories.UserRepository;
import com.course.autopodborplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private CompanyRepository companyRepository;
    private UserRepository userRepository;

    @Autowired
    public ClientController(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    //Главная страница
    @GetMapping("/")
    public String home(Model model, Principal principal) {

        Iterable<Company> companies = companyRepository.findAll(); //Вывод из бд всех компаний
        model.addAttribute("companies", companies);
        model.addAttribute("user", userRepository.findByUsername(principal.getName()).get());
        return "home";
    }

    @GetMapping("/company/{id}") //Страница компании, ее описание
    public String company(@PathVariable(value = "id") long id, Model model, Principal principal) {
        Company companies = new Company();
        Optional<Company> company = companyRepository.findById(id);
        ArrayList<Company> res = new ArrayList<>();
        company.ifPresent(res::add);
        model.addAttribute("company", res);
        model.addAttribute("user", userRepository.findByUsername(principal.getName()).get());
        return "company";
    }

    @GetMapping("/requests") //Страница заявок клиента
    public String requests(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()).get());
        return "requests";
    }
}