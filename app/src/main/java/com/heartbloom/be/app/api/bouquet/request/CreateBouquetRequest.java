package com.heartbloom.be.app.api.bouquet.request;

import java.util.List;

public record CreateBouquetRequest (
        /* 상대방에게 보여질 이름 */
        String displayName, // 6자 이내

        /* 상대방과의 관계 */
        String relationName,

        /* 꽃다발 타입 ID */
        Long bouquetTypeId,

        List<CreateBouquetAnswerRequest> answers

) {}