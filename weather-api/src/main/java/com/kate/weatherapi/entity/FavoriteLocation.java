package com.kate.weatherapi.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "favorite_locations")
@Getter
@Setter
@Builder
public class FavoriteLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String city;

    private String country;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public FavoriteLocation(){

    }

    public FavoriteLocation(int id, String city, String country, User user) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.user = user;
    }


}
