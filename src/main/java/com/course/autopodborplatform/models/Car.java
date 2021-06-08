package com.course.autopodborplatform.models;

import javax.persistence.*;

@Entity
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private int price;

    @Column(nullable = true)
    private String carBrand;

    @Column(nullable = true)
    private String carModel;

    @Column(nullable = true)
    private String img;

    @Column(nullable = true)
    private String description;

    @ManyToOne
    private Request request;

    public Car() {
    }

    public Car(Long id, int price, String carBrand, String carModel, String img, String description, Request request) {
        this.id = id;
        this.price = price;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.img = img;
        this.description = description;
        this.request = request;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
