package com.course.autopodborplatform.controllers;

import com.course.autopodborplatform.models.Car;
import com.course.autopodborplatform.models.Company;
import com.course.autopodborplatform.models.Request;
import com.course.autopodborplatform.models.User;
import com.course.autopodborplatform.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    public ClientController(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

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
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("requests", user.getRequests());
        model.addAttribute("user", user);
        return "requests";
    }

    @GetMapping("/company/{id}/request") //Страница оформления заявки в компанию
    public String addRequest(@PathVariable(value = "id") long id, Model model, Principal principal) {

        model.addAttribute("request", new Request());
        model.addAttribute("company_id", id);

        model.addAttribute("user", userRepository.findByUsername(principal.getName()).get());
        return "add_request";
    }

    @PostMapping("/add_request")
    public String add_request(@ModelAttribute("request") Request request, @ModelAttribute("company_id")  long id,
                              Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        Company company = companyRepository.getById(id);
        request.setUser(user);
        request.setCompany(company);
        request.setStatus(statusRepository.getById(0));
        if (request.getCarBrand().equals("")) {
            request.setCarBrand("Не указано");
        }
        if (request.getCarModel().equals("")) {
            request.setCarModel("Не указано");
        }
        requestRepository.save(request);
        return "redirect:/requests";
    }

    @GetMapping("/requests/{id}/cars")
    public String choose_car(@PathVariable(value = "id") long id, Model model, Principal principal) {
        model.addAttribute("cars" ,requestRepository.getById(id).getCars());
        model.addAttribute("user", userRepository.findByUsername(principal.getName()).get());
        return "cars_for_client";
    }
    @PostMapping("/choose_car")
    public String choose_car(@ModelAttribute("request_id")  long idR, @ModelAttribute("car_id")  long idC, Principal principal) {
        Request request = requestRepository.getById(idR);
        request.setStatus(statusRepository.getById(3));
        requestRepository.save(request);
        for(Car c:
            carRepository.findAll()){
            if(c.getId()!=idC&&c.getRequest().getId()==idR)
                carRepository.deleteById(c.getId());
        }
        return "redirect:/requests";
    }
}