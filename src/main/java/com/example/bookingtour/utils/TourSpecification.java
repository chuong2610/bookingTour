package com.example.bookingtour.utils;

import com.example.bookingtour.entity.TourEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TourSpecification {
    public static Specification<TourEntity> containsKeyword(String keyword){
        return ((root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.isEmpty()){
                return criteriaBuilder.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();
            String likePattern = "%" + keyword.trim().toLowerCase() + "%";

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("duration")), likePattern));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });
    }
}
