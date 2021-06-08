package com.course.autopodborplatform.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable = true)
    private String name;

    @Column(nullable = true)
    private String description;

    @OneToOne
    private User user;

    @OneToMany(
            mappedBy="company",
            cascade=CascadeType.ALL,
            orphanRemoval=true
    )
    private List<Request> requests;

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company(Long id, String name, String description, User user, List<Request> requests) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.requests = requests;
    }

    public Company() {
    }
}
