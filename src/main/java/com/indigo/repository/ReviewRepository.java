package com.indigo.repository;

import com.indigo.entity.Property;
import com.indigo.entity.PropertyUser;
import com.indigo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //@Query("select r from Review r where r.property=:property and r.propertyUser=:user ")
    // Review findReveiwByUser(@Param("property") Property property,@Param("user") PropertyUser user  );


@Query("select r from Review r where r.property=:property and r.propertyUser=:user")
    Review findReviewByUser(@Param("property") Property property,@Param("user") PropertyUser user);

}