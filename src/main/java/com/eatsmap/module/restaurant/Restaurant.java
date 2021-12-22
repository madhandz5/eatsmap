package com.eatsmap.module.restaurant;

import com.eatsmap.module.review.Review;
import com.eatsmap.module.review.dto.CreateReviewRequest;
import lombok.*;
import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_seq", initialValue = 1001, allocationSize = 30)
@Builder(access = AccessLevel.PRIVATE)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @Column(name = "restaurant_id")
    private Long id;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews = new ArrayList<>();

    private String resName;
    private String address;
    private Point location;


    public void createRestaurant(CreateReviewRequest request) {
        this.resName = request.getResName();
        this.address = request.getAddress();
        this.location = new Point(request.getX(), request.getY());
    }

//    public static Restaurant createRestaurant(CreateReviewRequest request) {
//        return Restaurant.builder()
//                .resName(request.getResName())
//                .address(request.getAddress())
//                .location(new Point(request.getX(), request.getY()))
//                .build();
//    }
}
