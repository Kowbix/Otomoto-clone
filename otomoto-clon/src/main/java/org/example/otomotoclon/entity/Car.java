package org.example.otomotoclon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "model_id")
    private Model model;
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "generation_id")
    private Generation generation;
    @Column(name = "year_production")
    private int yearProduction;
    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "bodywork_type_id")
    private BodyworkType bodyworkType;
    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;
    private int millage;
    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "damage_condition_id")
    private DamageCondition damageCondition;
    private String vin;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private List<Image> images;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id", referencedColumnName = "id")
    private Engine engine;
    @ManyToOne(
            optional = false,
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;
    @Column(name = "door_count")
    private int doorCount;
    @Column(name = "seat_count")
    private int seatCount;

}
