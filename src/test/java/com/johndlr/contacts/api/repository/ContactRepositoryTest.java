package com.johndlr.contacts.api.repository;

import com.johndlr.contacts.api.entity.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ContactRepositoryTest {

    @Autowired
    ContactRepository contactRepository;

    @Test
    void shouldFindContactByPhoneNumberWhenExists(){
        Contact contact = new Contact(1L,"John","Doe","5512345678","john@example.com");
        contactRepository.save(contact);
        Contact contactFound = contactRepository.findByPhoneNumber("5512345678").get();
        assertThat(contactFound).isNotNull();
        assertThat(contactFound.getName()).isEqualTo("John");
        assertThat(contactFound.getLastName()).isEqualTo("Doe");
        assertThat(contactFound.getPhoneNumber()).isEqualTo("5512345678");
        assertThat(contactFound.getEmail()).isEqualTo("john@example.com");
    }
}
