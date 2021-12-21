package com.eatsmap.module.Category;

import com.eatsmap.module.Category.dto.CreateCategoryRequest;
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

    public static Category createCategory(CreateCategoryRequest request) {
        return Category.builder()
                .categoryCode(request.getCategoryCode())
                .categoryName(request.getCategoryName())
                .build();
    }

}
