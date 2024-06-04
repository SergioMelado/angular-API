package com.tour_of_heroes.api.heroes.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HEROES")
public class Hero {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

    public Hero() {

    }

    public Hero merge(Hero target) {

        if(name != null && !name.isEmpty() && !name.equals(target.name)) target.name = name;
        return target;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}