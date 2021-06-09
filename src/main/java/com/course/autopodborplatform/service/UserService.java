package com.course.autopodborplatform.service;

import com.course.autopodborplatform.models.Company;
import com.course.autopodborplatform.models.Role;
import com.course.autopodborplatform.models.User;
import com.course.autopodborplatform.repositories.CompanyRepository;
import com.course.autopodborplatform.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {
    private final UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(UserRepr userRepr, Role role) {
        User user = new User();
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));
        user.setRole(role);
        if (role.getID()==1) {
            Company company = new Company();
            companyRepository.save(company);
            user.setCompany(company);
            repository.save(user);
            company.setUser(user);
            companyRepository.save(company);
        }
        else  {
            repository.save(user);
        }
    }
}
