package com.yoti.test.database.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The {@code CleaningConditionEntity} class represents certain conditions of a completed clean-up operation.
 *
 */

@Entity
@Table(name = "cleaning_condition")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CleaningConditionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cleaning_condition_seq")
    @SequenceGenerator(name = "cleaning_condition_seq", sequenceName = "cleaning_condition_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private LocalDateTime createdAt;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private String data;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cleaningCondition")
    private CleaningResultEntity cleaningResult;
}
