package com.allencam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDate;

public record TestimonialDTO(

        // For any changes to this record, update TestimonialGoogleSheetsService accordingly.
        // You will need to update the end column for 'String range' as well as
        // the row mapper.

        String timestamp,

        @UUID
        String testimonialId,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfService,

        @Min(1) @Max(5)
        Integer rating,

        @Size(max = 500)
        String message,

        @Email @JsonIgnore
        String email,

        @JsonIgnore
        boolean isActive
) {
        @JsonProperty("displayName")
        public String getDisplayName() {
                if (firstName != null && lastName != null && !lastName.isBlank()) {
                        return firstName + " " + lastName.charAt(0) + ".";
                }
                return firstName;
        }
}
