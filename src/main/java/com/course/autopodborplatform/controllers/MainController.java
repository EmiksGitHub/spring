package com.course.autopodborplatform.controllers;

import com.course.autopodborplatform.models.Company;
import com.course.autopodborplatform.repositories.CompanyRepositoriyI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CompanyRepositoriyI companyRepositoriyI;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Company> companies = companyRepositoriyI.findAll();
//        model.addAttribute("title", "Главная страница");
        model.addAttribute("companies", companies);
        return "home";
    }

}