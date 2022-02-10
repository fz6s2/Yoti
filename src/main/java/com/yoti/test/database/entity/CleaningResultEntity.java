package com.yoti.test.database.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cleaning_result")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CleaningResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cleaning_result_seq")
    @SequenceGenerator(name = "cleaning_condition_seq", sequenceName = "cleaning_result_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private String data;

    @OneToOne
    @JoinColumn(name = "cleaning_condition_id")
    private CleaningConditionEntity cleaningCondition;
}
