package com.m1guelsb.blog.dtos;

import java.time.Instant;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ArticleDto(
        Long id,

        @NotBlank(message = "title: Is required") @Length(min = 3, max = 100, message = "title: Must be of 3 - 100 characters") String title,

        @NotBlank(message = "brief: Is required") String brief,

        @NotBlank(message = "body: Is required") String body,

        @NotEmpty(message = "categoryIds: Must have at least one category") Set<Long> categoryIds,

        Instant createdAt,

        Instant updatedAt

) {
}
