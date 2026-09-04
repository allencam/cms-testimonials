package com.allencam.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TestimonialDTO(

        String timestamp,

        @NotNull( message = "First name is required")
        String firstName,

        @NotNull( message = "Last name is required")
        String lastName,

        LocalDate dateOfService,

        @Min(1) @Max(5)
        Integer rating,

        @NotNull( message = "Message is required")
        String message,

        @Email
        String email,

        boolean isActive
) { }
