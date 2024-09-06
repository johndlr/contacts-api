package com.johndlr.contacts.api.controller;

import com.johndlr.contacts.api.dto.ContactDto;
import com.johndlr.contacts.api.dto.ErrorResponseDto;
import com.johndlr.contacts.api.dto.ResponseDto;
import com.johndlr.contacts.api.entity.Contact;
import com.johndlr.contacts.api.constants.ContactConstants;
import com.johndlr.contacts.api.service.IContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Contact Management",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE contacts details"
)
@RestController
@RequestMapping(path="/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class ContactController {

    private final IContactService iContactService;

    @Operation(
            summary = "Create Contact REST API",
            description = "REST API to create new contact"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createContact(@Valid @RequestBody ContactDto contactDto){
        iContactService.createContact(contactDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(ContactConstants.STATUS_201,ContactConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Contact Details REST API",
            description = "REST API to fetch Contact"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contact")
    public ResponseEntity<ContactDto> fetchContact(
            @NotEmpty(message = "Phone Number can not be a null or empty")
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
            @RequestParam String phoneNumber){

        ContactDto contactDto = iContactService.fetchContact(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(contactDto);

    }

    @Operation(
            summary = "Fetch Contacts Details REST API",
            description = "REST API to fetch Contacts"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/contacts/pagination")
    public ResponseEntity<Page<Contact>> fetchContactsPaginationAndSorting(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam String sortProperty){
        return ResponseEntity.status(HttpStatus.OK).body(iContactService.fetchContactsPaginationAndSorting(pageNumber, pageSize, sortProperty));
    }

    @Operation(
            summary = "Delete Contact Details REST API",
            description = "REST API to delete Contact details based on a phone number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteContact(
            @NotEmpty(message = "Phone Number can not be a null or empty")
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
            @RequestParam String phoneNumber){
        boolean isDeleted = iContactService.deleteContact(phoneNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ContactConstants.STATUS_200, ContactConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ContactConstants.STATUS_417, ContactConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Update Contact Details REST API",
            description = "REST API to update Contact based on a phone number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateContact(@Valid @RequestBody ContactDto contactDto){
        boolean isUpdated = iContactService.updateContact(contactDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ContactConstants.STATUS_200, ContactConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ContactConstants.STATUS_417, ContactConstants.MESSAGE_417_UPDATE));
        }
    }

}
