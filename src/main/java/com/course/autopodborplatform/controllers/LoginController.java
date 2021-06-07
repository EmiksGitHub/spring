package com.course.autopodborplatform.controllers;

import com.course.autopodborplatform.service.UserRepr;
import org.springframework.beans.factory.annotation.Autowired;
import com.course.autopodborplatform.service.UserService;
import com.course.autopodborplatform.repositories.RoleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.Id;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public LoginController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRepr());
        return "register";
    }
    @PostMapping("/register")
    public String registerNewUser(@Valid UserRepr userRepr, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!userRepr.getPassword().equals(userRepr.getRepeatPassword())){
            bindingResult.rejectValue("password","","Пароли не совпадают");
            return "register";
        }
        userService.create(userRepr, roleRepository.getById(userRepr.getRole_id()));
        return "redirect:/login";
    }
}
