package com.course.autopodborplatform.controllers;

import com.course.autopodborplatform.models.Company;
import com.course.autopodborplatform.repositories.CompanyRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private CompanyRepositoryI companyRepositoryI;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Company> companies = companyRepositoryI.findAll();
//        model.addAttribute("title", "Главная страница");
        model.addAttribute("companies", companies);
        return "home";
    }

    @GetMapping("/company/{id}")
    public String company(@PathVariable(value = "id") long id, Model model) {
        Company companies = new Company();
        Optional<Company> company = companyRepositoryI.findById(id);
        ArrayList<Company> res = new ArrayList<>();
        company.ifPresent(res::add);
        model.addAttribute("company", res);
        return "company";
    }

}