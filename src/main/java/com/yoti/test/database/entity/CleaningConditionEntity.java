package com.yoti.test.database.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class CleaningConditionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cleaning_condition_seq")
    @SequenceGenerator(name = "cleaning_condition_seq", sequenceName = "cleaning_condition_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column
    private LocalDateTime createdAt;

    @NotNull
    @Column
    private String data;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cleaningCondition")
    private CleaningResultEntity cleaningResult;
}
