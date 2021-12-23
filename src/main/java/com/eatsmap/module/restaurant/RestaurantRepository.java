package com.eatsmap.module.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryExtension {
    Restaurant findByResNameAndAddress(String resName, String address);
}
