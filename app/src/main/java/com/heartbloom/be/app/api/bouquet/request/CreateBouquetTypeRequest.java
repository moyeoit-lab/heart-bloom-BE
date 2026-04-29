package com.heartbloom.be.app.api.bouquet.request;

import jakarta.validation.constraints.NotBlank;

public record CreateBouquetTypeRequest (

       @NotBlank String bouquetName,
       String bouquetDescription,
       String bouquetImageUrl,
       Boolean active
) {

    public CreateBouquetTypeRequest {
        if (active == null) active = true;
    }

}