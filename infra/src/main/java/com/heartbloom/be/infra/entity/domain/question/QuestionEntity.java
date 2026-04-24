package com.heartbloom.be.infra.entity.domain.question;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_question")
@Entity
public class QuestionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "answer_type", nullable = false, length = 20)
    private String answerType;

    @Builder
    public QuestionEntity(Long id,
                          String title,
                          String description,
                          String answerType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.answerType = answerType != null ? answerType : "REQUIRED";
    }

}
