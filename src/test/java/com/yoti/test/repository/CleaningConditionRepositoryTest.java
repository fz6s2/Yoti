package com.yoti.test.repository;

import com.yoti.test.database.entity.CleaningConditionEntity;
import com.yoti.test.database.entity.CleaningResultEntity;
import com.yoti.test.database.repository.CleaningConditionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("unittest")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CleaningConditionRepositoryTest {

    @Autowired
    CleaningConditionRepository cleaningConditionRepository;

    @Test
    void successfulSaveTest() {
        CleaningConditionEntity entity = getCorrectCleaningConditionEntity();
        cleaningConditionRepository.save(entity);

        List<CleaningConditionEntity> entityList = cleaningConditionRepository.findAll();

        assertEquals(entityList.size(), 1);

        CleaningConditionEntity entityDb = entityList.get(0);

        assertNotNull(entityDb);
        assertEquals(entity.getData(), entityDb.getData());
        assertEquals(entity.getCleaningResult().getData(), entityDb.getCleaningResult().getData());
    }

    @Test
    void successfulPageableFindTest() {
        Pageable pageable = PageRequest.of(0, 1);

        CleaningConditionEntity entity = getCorrectCleaningConditionEntity();
        cleaningConditionRepository.save(entity);
        cleaningConditionRepository.save(entity);

        Page<CleaningConditionEntity> entityPage = cleaningConditionRepository.findAll(pageable);

        assertEquals(entityPage.getSize(), 1);

        CleaningConditionEntity entityDb = entityPage.getContent().get(0);

        assertNotNull(entityDb);
        assertEquals(entity.getData(), entityDb.getData());
        assertEquals(entity.getCleaningResult().getData(), entityDb.getCleaningResult().getData());
    }

    CleaningConditionEntity getCorrectCleaningConditionEntity() {
        CleaningConditionEntity conditionEntity = new CleaningConditionEntity()
            .setCreatedAt(LocalDateTime.now())
            .setData("{}");
        CleaningResultEntity resultEntity = new CleaningResultEntity()
            .setCreatedAt(LocalDateTime.now())
            .setData("{[]}")
            .setCleaningCondition(conditionEntity);

        conditionEntity.setCleaningResult(resultEntity);

        return conditionEntity;
    }
}
