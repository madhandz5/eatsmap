package com.eatsmap.infra.utils.file;

import com.eatsmap.module.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<Fileinfo, Long> {

    List<Fileinfo> getByReviewAndDeleted(Review review, boolean deleted);
}
