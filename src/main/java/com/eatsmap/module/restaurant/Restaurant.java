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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "restaurant_seq", sequenceName = "restaurant_seq", initialValue = 1001, allocationSize = 30)
@Builder(access = AccessLevel.PRIVATE)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_seq")
    @Column(name = "restaurant_id")
    private Long id;

    private String resName;
    private String address;
    private Point location;

    public static Restaurant createRestaurant(String resName, String address, double x, double y) {
        return Restaurant.builder()
                .resName(resName)
                .address(address)
                .location(new Point(x, y))
                .build();
    }
}
