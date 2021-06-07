package com.course.autopodborplatform.controllers;

import com.course.autopodborplatform.models.Company;
import com.course.autopodborplatform.repositories.CompanyRepository;
import com.course.autopodborplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    private UserRepository userRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    //Страница заявок у компани
    @GetMapping("/company_requests")
    public String requests(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()).get());
        return "company_requests";
    }
}