package com.johndlr.contacts.api.service;

import com.johndlr.contacts.api.dto.ContactDto;
import com.johndlr.contacts.api.entity.Contact;
import com.johndlr.contacts.api.exception.ContactNotFoundException;
import com.johndlr.contacts.api.repository.ContactRepository;
import com.johndlr.contacts.api.service.implementation.ContactServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    private Contact contactForTesting;

    private Optional<Contact> optionalForTesting;

    private ContactDto contactDtoForTesting;

    private ContactDto invalidContactDtoForTesting;

    private String phoneNumber;

    @BeforeEach
    void setUp(){
        contactForTesting = new Contact(1L, "John", "Doe", "5512345678", "john@example.com");
        optionalForTesting = Optional.of(contactForTesting);
        contactDtoForTesting = new ContactDto("John","Doe","5512345678","john@example.com");
        invalidContactDtoForTesting = new ContactDto("Dave","Orlok","5512345688","dave@example.com");
        phoneNumber = "5512345688";
    }


    @Test
    void shouldFindContactWhenExists(){
        when(contactRepository.findByPhoneNumber("5512345678")).thenReturn(optionalForTesting);
        ContactDto contactDto = contactService.fetchContact("5512345678");
        assertThat(contactDto).isNotNull();
        assertThat(contactDto.getEmail()).isEqualTo("john@example.com");
        assertThat(contactDto.getName()).isEqualTo("John");
        assertThat(contactDto.getLastName()).isEqualTo("Doe");
    }

    @Test
    void shouldReturnContactNotFoundExceptionForNonExistingContact(){
        when(contactRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {contactService.fetchContact(phoneNumber);})
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessage("Contact with given phone number " + phoneNumber + " doesn't exists");
    }

    @Test
    void shouldReturnTrueForUpdateExistingContact(){
        when(contactRepository.findByPhoneNumber("5512345678")).thenReturn(optionalForTesting);
        boolean booleanValue = contactService.updateContact(contactDtoForTesting);
        assertThat(booleanValue).isTrue();
    }

    @Test
    void shouldReturnContactNotFoundExceptionForUpdateNoExistingContact(){
        when(contactRepository.findByPhoneNumber(invalidContactDtoForTesting.getPhoneNumber())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {contactService.updateContact(invalidContactDtoForTesting);})
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessage("Contact with given phone number " + invalidContactDtoForTesting.getPhoneNumber() + " doesn't exists");
    }

}
