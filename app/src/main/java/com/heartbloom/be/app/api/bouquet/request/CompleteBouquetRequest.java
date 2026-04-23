package com.heartbloom.be.app.api.bouquet.request;

import java.util.List;

public record CompleteBouquetRequest(
        List<CreateBouquetAnswerRequest> answers
) {}
