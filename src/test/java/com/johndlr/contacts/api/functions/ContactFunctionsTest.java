package com.johndlr.contacts.api.functions;

import com.johndlr.contacts.api.dto.ContactDto;
import com.johndlr.contacts.api.dto.PageDto;
import com.johndlr.contacts.api.entity.Contact;
import com.johndlr.contacts.api.exception.ContactAlreadyExistsException;
import com.johndlr.contacts.api.exception.ContactNotFoundException;
import com.johndlr.contacts.api.repository.ContactRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContactFunctionsTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private ContactFunctions contactFunctions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateContact() {
        ContactDto contactDto = new ContactDto();
        contactDto.setPhoneNumber("1234567890");

        when(contactRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());

        contactFunctions.createContact().accept(contactDto);

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void testCreateContactAlreadyExists() {
        ContactDto contactDto = new ContactDto();
        contactDto.setPhoneNumber("1234567890");
        Contact contact = new Contact(1L,"Tony", "Stark", "1234567890", "stark@example.com");

        when(contactRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(contact));

        assertThrows(ContactAlreadyExistsException.class, () -> contactFunctions.createContact().accept(contactDto));
    }

    @Test
    void testDeleteContact() {
        Contact contact = new Contact();
        when(contactRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(contact));

        contactFunctions.deleteContact().accept("1234567890");

        verify(contactRepository, times(1)).delete(any(Contact.class));
    }

    @Test
    void testDeleteContactNotFound() {
        when(contactRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> contactFunctions.deleteContact().accept("1234567890"));
    }

    @Test
    void testUpdateContact() {
        ContactDto contactDto = new ContactDto();
        contactDto.setPhoneNumber("1234567890");
        Contact contact = new Contact();

        when(contactRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(contact));

        contactFunctions.updateContact().accept(contactDto);

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void testUpdateContactNotFound() {
        ContactDto contactDto = new ContactDto();
        contactDto.setPhoneNumber("1234567890");

        when(contactRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> contactFunctions.updateContact().accept(contactDto));
    }

    @Test
    void testFetchContact() {
        Contact contact = new Contact();
        when(contactRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(contact));

        ContactDto result = contactFunctions.fetchContact().apply("1234567890");

        assertNotNull(result);
    }

    @Test
    void testFetchContactNotFound() {
        when(contactRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> contactFunctions.fetchContact().apply("1234567890"));
    }

    @Test
    void testFetchContacts() {
        PageDto pageDto = new PageDto(0, 10, "name");
        Page<Contact> page = new PageImpl<>(Collections.emptyList());
        when(contactRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Contact> result = contactFunctions.fetchContacts().apply(pageDto);

        assertNotNull(result);
    }
}