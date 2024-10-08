package com.johndlr.contacts.api.functions;

import com.johndlr.contacts.api.dto.ContactDto;
import com.johndlr.contacts.api.dto.PageDto;
import com.johndlr.contacts.api.entity.Contact;
import com.johndlr.contacts.api.exception.ContactAlreadyExistsException;
import com.johndlr.contacts.api.exception.ContactNotFoundException;
import com.johndlr.contacts.api.mapper.ContactMapper;
import com.johndlr.contacts.api.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ContactFunctions {

    private final ContactRepository contactRepository;
    private final Validator validator;
    private static final Logger logger = LoggerFactory.getLogger(ContactFunctions.class);

    @Bean
    public Consumer<ContactDto> createContact() {
        return contactDto -> {
            validate(contactDto);
            Contact contact = ContactMapper.mapToContact(contactDto, new Contact());
            Optional<Contact> optionalContact = contactRepository.findByPhoneNumber(contactDto.getPhoneNumber());
            if (optionalContact.isPresent()) {
                throw new ContactAlreadyExistsException("Contact already registered with given phoneNumber " + contactDto.getPhoneNumber());
            }
            contactRepository.save(contact);
            logger.info("Contact created successfully: {}", contactDto);
        };
    }

    @Bean
    public Consumer<String> deleteContact() {
        return phoneNumber -> {
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                throw new IllegalArgumentException("Phone number must be provided");
            }
            Contact contact = contactRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new ContactNotFoundException("Contact with given phone number " + phoneNumber + " doesn't exist"));
            contactRepository.delete(contact);
            logger.info("Contact deleted successfully: {}", phoneNumber);
        };
    }

    @Bean
    public Consumer<ContactDto> updateContact() {
        return contactDto -> {
            validate(contactDto);
            Contact contact = contactRepository.findByPhoneNumber(contactDto.getPhoneNumber())
                    .orElseThrow(() -> new ContactNotFoundException("Contact with given phone number " + contactDto.getPhoneNumber() + " doesn't exist"));
            ContactMapper.mapToContact(contactDto, contact);
            contactRepository.save(contact);
            logger.info("Contact updated successfully: {}", contactDto);
        };
    }

    @Bean
    public Function<String, ContactDto> fetchContact() {
        return phoneNumber -> {
            Contact contact = contactRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new ContactNotFoundException("Contact with given phone number " + phoneNumber + " doesn't exist"));
            logger.info("Contact fetched successfully: {}", phoneNumber);
            return ContactMapper.mapToContactDto(contact, new ContactDto());
        };
    }

    @Bean
    public Function<PageDto, Page<Contact>> fetchContacts() {
        return pageDto -> {
            validate(pageDto);
            Pageable pageable = PageRequest.of(pageDto.pageNumber(), pageDto.pageSize(), Sort.by(pageDto.sortProperty()).ascending());
            logger.info("Fetching contacts with pagination: {}", pageDto);
            return contactRepository.findAll(pageable);
        };
    }

    private void validate(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Object> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
    }
}
