package com.tour_of_heroes.api.shop.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Table (name = "PRODUCTS")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID", unique = true, nullable = false, length = 12)
    private int id;

    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "DESCRIPTION", length = 50)
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrderList;

    public Product() {

    }

    public Product(int id, String name, Double price, String description) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Product(String name, String description, Double price) {

        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product merge (Product target) {

        if(name != null && !name.isEmpty() && !name.equals(target.name)) target.name = name;
        if(price != null && !price.equals(target.price)) target.price = price;
        if(!description.equals(target.description)) target.description = description;
        return target;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}