package com.eatsmap.module.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant createNewRestaurant(String resName, String address, double x, double y) {
        Restaurant restaurant = Restaurant.createRestaurant(resName, address, x, y);
        return restaurantRepository.saveAndFlush(restaurant);
    }

    public Restaurant getRestaurant(String resName, String address) {
        return restaurantRepository.findByResNameAndAddress(resName, address);
    }
}
