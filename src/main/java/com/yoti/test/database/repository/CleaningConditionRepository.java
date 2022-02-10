package com.yoti.test.database.repository;

import com.yoti.test.database.entity.CleaningConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CleaningConditionRepository extends JpaRepository<CleaningConditionEntity, Long>,
    PagingAndSortingRepository<CleaningConditionEntity, Long> {
}
