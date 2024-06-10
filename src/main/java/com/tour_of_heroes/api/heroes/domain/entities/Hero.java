package com.tour_of_heroes.api.heroes.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "HEROES")
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="heroes_seq")
    @SequenceGenerator(name="heroes_seq",sequenceName="HEROES_SEQ", allocationSize=1)
    @Column(name = "ID")
    private int id;

    @Setter
    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

    public Hero() {

    }

    public Hero(String name) {
        this.name = name;
    }

    public Hero(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hero merge(Hero target) {

        if(name != null && !name.isEmpty() && !name.equals(target.name)) target.name = name;
        return target;
    }
}