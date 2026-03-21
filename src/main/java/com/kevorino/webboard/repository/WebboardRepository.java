package com.kevorino.webboard.repository;

import com.kevorino.webboard.entity.Webboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface WebboardRepository extends JpaRepository<Webboard, Long>,
        QuerydslPredicateExecutor<Webboard> {
}
