package com.johndlr.contacts.api.dto;

import jakarta.validation.constraints.*;

public record PageDto(
        @NotNull(message = "Page number cannot be null")
        @Min(value = 0, message = "Page number must be greater than or equal to 0")
        Integer pageNumber,

        @NotNull(message = "Page size cannot be null")
        @Min(value = 1, message = "Page size must be greater than or equal to 1")
        Integer pageSize,

        @NotNull(message = "Sort property cannot be null")
        @Size(min = 1, message = "Sort property must not be empty")
        String sortProperty
) {
}
