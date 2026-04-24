package com.heartbloom.be.app.api.bouquet.request;

import java.util.List;

public record CompleteBouquetRequest(
        String receiverName,
        List<CreateBouquetAnswerRequest> answers
) {}
