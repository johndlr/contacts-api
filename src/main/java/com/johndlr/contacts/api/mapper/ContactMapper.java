package com.johndlr.contacts.api.mapper;

import com.johndlr.contacts.api.dto.ContactDto;
import com.johndlr.contacts.api.entity.Contact;

public class ContactMapper {
    public static ContactDto mapToContactDto(Contact contact, ContactDto contactDto){
        contactDto.setName(contact.getName());
        contactDto.setLastName(contact.getLastName());
        contactDto.setPhoneNumber(contact.getPhoneNumber());
        contactDto.setEmail(contact.getEmail());
        return contactDto;
    }

    public static Contact mapToContact(ContactDto contactDto, Contact contact){
        contact.setName(contactDto.getName());
        contact.setLastName(contactDto.getLastName());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        contact.setEmail(contactDto.getEmail());
        return contact;
    }
}
