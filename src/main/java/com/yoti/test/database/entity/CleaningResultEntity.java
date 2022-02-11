package com.yoti.test.database.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The {@code CleaningResultEntity} class represents a result of a completed clean-up operation.
 *
 */

@Entity
@Table(name = "cleaning_result")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CleaningResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cleaning_result_seq")
    @SequenceGenerator(name = "cleaning_result_seq", sequenceName = "cleaning_result_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column
    private LocalDateTime createdAt;

    @NotNull
    @Column
    private String data;

    @NotNull
    @OneToOne
    @JoinColumn(name = "cleaning_condition_id")
    private CleaningConditionEntity cleaningCondition;
}
