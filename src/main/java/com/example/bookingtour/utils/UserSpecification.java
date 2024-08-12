package com.example.bookingtour.utils;

import com.example.bookingtour.entity.UserEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<UserEntity> containsKeyword(String keyword){
        return ((root, query, criteriaBuilder) -> {
            if(keyword == null || keyword.isEmpty()){
                return criteriaBuilder.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();
            String likePattern = "%" + keyword.trim().toLowerCase() + "%";

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullname")), likePattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), likePattern));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });
    }

}
