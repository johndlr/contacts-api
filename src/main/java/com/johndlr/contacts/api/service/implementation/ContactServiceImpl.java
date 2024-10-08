package com.johndlr.contacts.api.service.implementation;

import com.johndlr.contacts.api.dto.ContactDto;
import com.johndlr.contacts.api.entity.Contact;
import com.johndlr.contacts.api.mapper.ContactMapper;
import com.johndlr.contacts.api.repository.ContactRepository;
import com.johndlr.contacts.api.service.IContactService;
import com.johndlr.contacts.api.exception.ContactAlreadyExistsException;
import com.johndlr.contacts.api.exception.ContactNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    @Override
    public void createContact(ContactDto contactDto) {
        Contact contact = ContactMapper.mapToContact(contactDto, new Contact());
        Optional<Contact> optionalContact = contactRepository.findByPhoneNumber(contactDto.getPhoneNumber());
        if (optionalContact.isPresent()) {
            throw new ContactAlreadyExistsException("Contact already registered with given phoneNumber " + contactDto.getPhoneNumber());
        }
        contactRepository.save(contact);
    }

    @Override
    public boolean deleteContact(String phoneNumber) {
        Contact contact = contactRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new ContactNotFoundException("Contact with given phone number " + phoneNumber + " doesn't exists"));
        contactRepository.delete(contact);
        return true;
    }

    @Override
    public boolean updateContact(ContactDto contactDto) {
        Contact contact = contactRepository.findByPhoneNumber(contactDto.getPhoneNumber())
                .orElseThrow(()-> new ContactNotFoundException("Contact with given phone number " + contactDto.getPhoneNumber() + " doesn't exists"));
        ContactMapper.mapToContact(contactDto, contact);
        contactRepository.save(contact);
        return true;
    }

    @Override
    public ContactDto fetchContact(String phoneNumber) {
        Contact contact = contactRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new ContactNotFoundException("Contact with given phone number " + phoneNumber + " doesn't exists"));
        return ContactMapper.mapToContactDto(contact, new ContactDto());
    }

    @Override
    public Page<Contact> fetchContacts(Integer pageNumber, Integer pageSize, String sortProperty) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortProperty).ascending());
        return contactRepository.findAll(pageable);
    }

}
