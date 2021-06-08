package com.course.autopodborplatform.controllers;

import com.course.autopodborplatform.models.Car;
import com.course.autopodborplatform.models.Company;
import com.course.autopodborplatform.models.Request;
import com.course.autopodborplatform.models.User;
import com.course.autopodborplatform.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    //Страница заявок у компани
    @GetMapping("/company_requests")
    public String requests(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();

        model.addAttribute("user", user);
        model.addAttribute("Defrequests", requestRepository.getCompaniesRequestByDefaultStatus(user.getCompany().getId()));
        model.addAttribute("InProcessrequests", requestRepository.getCompaniesRequestByInProcessStatus(user.getCompany().getId()));
        model.addAttribute("finishrequests", requestRepository.getCompaniesRequestByFinishStatus(user.getCompany().getId()));
        return "company_requests";
    }

    //Страница редактирования компании, описания и имя
    @GetMapping("/edit_company")
    public String edit_company(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getRole().getID()==0)
            return "redirect:/";
        model.addAttribute("company", user.getCompany());
        model.addAttribute("user", user);
        return "edit_company";
    }
    @PostMapping("/edit_companyP")
    public String edit_companyP(@ModelAttribute("company") Company company, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();

        Company realCompany = user.getCompany();
        realCompany.setDescription(company.getDescription());
        realCompany.setName(company.getName());
        companyRepository.save(realCompany);
        return "redirect:/";
    }

    @PostMapping("/denied_request")
    public String denied_request(@ModelAttribute("request_id1") long id, Principal principal) {
        requestRepository.deleteById(id);
        return "redirect:/company_requests";
    }
    @PostMapping("/access_request")
    public String access_request(@ModelAttribute("request_id") long id, Principal principal) {
        Request request =  requestRepository.getById(id);
        request.setStatus(statusRepository.getById(1));
        requestRepository.save(request);
        return "redirect:/company_requests";
    }

    @GetMapping("/{id}/add_car_to_request")
    public String add_car_to_request(@PathVariable(value = "id") long id, Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("car", new Car());
        model.addAttribute("user", user);
        model.addAttribute("id_request", id);
        model.addAttribute("maxPrice", requestRepository.getById(id).getPrice());
        model.addAttribute("error", "");
        return "add_car_to_request";
    }

    @PostMapping("/add_car_to_request")
    public String add_car_to_request(@ModelAttribute("id_request") long id, @ModelAttribute("car") Car car, Principal principal
    , Model model) {
        Request request = requestRepository.getById(id);
        User user = userRepository.findByUsername(principal.getName()).get();
        if (car.getPrice()>request.getPrice()) {
            model.addAttribute("car", new Car());
            model.addAttribute("user", user);
            model.addAttribute("id_request", id);
            model.addAttribute("maxPrice", requestRepository.getById(id).getPrice());
            model.addAttribute("error", "Клиент нищий");
            return "/add_car_to_request";
        }
        carRepository.save(car);
        if (request.getCars()==null) {
            List<Car> cars =new ArrayList<Car>();
            cars.add(car);
            request.setCars(cars);
        }
        else
            request.getCars().add(car);
        requestRepository.save(request);
        car.setRequest(request);
        carRepository.save(car);
        return "redirect:/company_requests";
    }

    @GetMapping("/{id}/cars")
    public String show_cars(@PathVariable(value = "id") long id, Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("cars", requestRepository.getById(id).getCars());
        if(requestRepository.getById(id).getStatus().getID()==3)
            model.addAttribute("flag", false);
        else
            model.addAttribute("flag", true);
        model.addAttribute("user", user);
        return "cars";
    }

    @PostMapping("/delete_car")
    public String delete_car(@ModelAttribute("car_id") long id, Principal principal) {
        long idR = carRepository.getById(id).getRequest().getId();
        carRepository.deleteById(id);
        return "redirect:/" + idR + "/cars";
    }

    @PostMapping("/finish_podbor")
    public String finish_podbor(@ModelAttribute("request_id2") long id, Principal principal) {
        Request request = requestRepository.getById(id);
        if (request.getCars().isEmpty())
            return "redirect:/company_requests";
        request.setStatus(statusRepository.getById(2));
        requestRepository.save(request);
        return "redirect:/company_requests";
    }
}
