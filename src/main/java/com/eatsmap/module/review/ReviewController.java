package com.eatsmap.module.review;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(path = "/create")
    public void createReview() {
        reviewService.createReview();
    }


}
