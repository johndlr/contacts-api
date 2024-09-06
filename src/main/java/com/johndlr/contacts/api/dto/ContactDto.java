package com.johndlr.contacts.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Contact",
        description = "Schema to hold Contact information"
)
public class ContactDto {

    @Schema(
            description = "Name of the contact", example = "Tony"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 2, max = 30, message = "The length of the contact name should be between 2 and 30")
    private String name;

    @Schema(
            description = "Last Name of the contact", example = "Stark"
    )
    @NotEmpty(message = "Last Name can not be a null or empty")
    @Size(min = 2, max = 30, message = "The length of the contact last name should be between 2 and 30")
    private String lastName;

    @Schema(
            description = "Phone Number of the contact", example = "5912354784"
    )
    @NotEmpty(message = "Phone Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Schema(
            description = "Email of the contact", example = "tonystark@example.com"
    )
    @Email(message = "Email address should be a valid value")
    private String email;

}
