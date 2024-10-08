package com.johndlr.contacts.api.dto;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 2, max = 30, message = "The length of the contact name should be between 2 and 30")
    private String name;


    @NotEmpty(message = "Last Name can not be a null or empty")
    @Size(min = 2, max = 30, message = "The length of the contact last name should be between 2 and 30")
    private String lastName;


    @NotEmpty(message = "Phone Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String phoneNumber;


    @Email(message = "Email address should be a valid value")
    private String email;

}
