package com.heartbloom.be.infra.entity.domain.answer;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_bouquet_answer")
@Entity
public class BouquetAnswerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bouquet_answer_id")
    private Long id;

    @Column(name = "bouquet_id", nullable = false)
    private Long bouquetId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "answer_type", nullable = false, length = 20)
    private String answerType;

    @Column(name = "respondent_type", nullable = false, length = 20)
    private String respondentType;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "bouquet_receiver_id")
    private Long bouquetReceiverId;

    @Column(name = "subjective_content", columnDefinition = "TEXT")
    private String subjectiveContent;

    @Column(name = "selected_option_id")
    private Long selectedOptionId;

    @Builder
    public BouquetAnswerEntity(Long bouquetId, Long questionId, String answerType, String respondentType,
                         Long userId, Long bouquetReceiverId, String subjectiveContent, Long selectedOptionId) {
        this.bouquetId = bouquetId;
        this.questionId = questionId;
        this.answerType = answerType != null ? answerType : "SUBJECTIVE";
        this.respondentType = respondentType != null ? respondentType : "SENDER";
        this.userId = userId;
        this.bouquetReceiverId = bouquetReceiverId;
        this.subjectiveContent = subjectiveContent;
        this.selectedOptionId = selectedOptionId;
    }

}
