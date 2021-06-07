package com.course.autopodborplatform.controllers;

import com.course.autopodborplatform.models.Company;
import com.course.autopodborplatform.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private CompanyRepository companyRepository;

    //Главная страница
    @GetMapping("/")
    public String home(Model model) {
        Iterable<Company> companies = companyRepository.findAll(); //Вывод из бд всех компаний
        model.addAttribute("companies", companies);
        return "home";
    }

    @GetMapping("/company/{id}") //Страница компании, ее описание
    public String company(@PathVariable(value = "id") long id, Model model) {
        Company companies = new Company();
        Optional<Company> company = companyRepository.findById(id);
        ArrayList<Company> res = new ArrayList<>();
        company.ifPresent(res::add);
        model.addAttribute("company", res);
        return "company";
    }

    @GetMapping("/requests") //Страница заявок клиента
    public String requests(Model model) {
        return "requests";
    }
}