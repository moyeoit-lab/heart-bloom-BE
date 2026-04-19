package com.heartbloom.be.infra.entity.domain.question;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_question_option")
@Entity
public class QuestionOptionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_option_id")
    private Long id;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "option_text", nullable = false, length = 500)
    private String optionText;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Builder
    public QuestionOptionEntity(Long questionId, String optionText, Integer sortOrder) {
        this.questionId = questionId;
        this.optionText = optionText;
        this.sortOrder = sortOrder != null ? sortOrder : 0;
    }

}
