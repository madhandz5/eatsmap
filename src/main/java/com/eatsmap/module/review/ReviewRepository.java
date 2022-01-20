package com.eatsmap.module.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryExtension {

    List<Review> findByDeleted(boolean deleted);

//    List<Review> findTimeline();
}
