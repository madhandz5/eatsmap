package com.eatsmap.module.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryExtension {

    Category findByCategoryCode(String categoryCode);

    Category findByCategoryName(String categoryName);

    boolean existsByCategoryCode(String categoryCode);

    boolean existsByCategoryName(String categoryName);

    Category findByCategoryCodeAndDeleted(String categoryCode, boolean deleted);
}
