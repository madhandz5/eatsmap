package com.eatsmap.module.category;

import com.eatsmap.module.category.dto.CreateCategoryRequest;
import com.eatsmap.module.review.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(unique = true)
    private String categoryCode;

    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Review> reviews = new ArrayList<>();

    private boolean deleted;

    public static Category createCategory(CreateCategoryRequest request) {
        return Category.builder()
                .categoryCode(request.getCategoryCode())
                .categoryName(request.getCategoryName())
                .deleted(false)
                .build();
    }

    public void setReview(Review review) {
        this.getReviews().add(review);
        review.setCategory(this);
    }

    public void deleteCategory() {
        this.deleted = true;
    }
}
