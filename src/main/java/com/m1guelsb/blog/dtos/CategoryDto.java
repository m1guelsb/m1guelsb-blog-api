package com.m1guelsb.blog.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
    Long id,

    @NotBlank(message = "title: Is required") @Length(min = 3, max = 100, message = "title: Must be of 3 - 100 characters") String title) {

}
