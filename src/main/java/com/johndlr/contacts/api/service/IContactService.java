package com.johndlr.contacts.api.service;

import com.johndlr.contacts.api.dto.ContactDto;
import com.johndlr.contacts.api.entity.Contact;
import org.springframework.data.domain.Page;

public interface IContactService {
    void createContact(ContactDto contactDto);

    boolean deleteContact(String phoneNumber);

    boolean updateContact(ContactDto contactDto);

    ContactDto fetchContact(String phoneNumber);

    Page<Contact> fetchContactsPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortProperty);




}
