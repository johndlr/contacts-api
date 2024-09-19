package com.johndlr.contacts.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johndlr.contacts.api.constants.ContactConstants;
import com.johndlr.contacts.api.dto.ContactDto;
import com.johndlr.contacts.api.service.IContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IContactService contactService;

    private ContactDto contactDto;

    private String validPhoneNumber;

    private String invalidPhoneNumber;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        validPhoneNumber = "5515161718";
        contactDto = new ContactDto("John","Doe",validPhoneNumber,"jdoe@example.com");
        objectMapper = new ObjectMapper();
        invalidPhoneNumber = "551415";
    }

    @Test
    public void testFetchContactValidPhoneNumber() throws Exception {

        when(contactService.fetchContact(validPhoneNumber)).thenReturn(contactDto);
        mockMvc.perform(get("/api/v1/contact").param("phoneNumber", validPhoneNumber))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.phoneNumber").value(validPhoneNumber))
                .andExpect(jsonPath("$.email").value("jdoe@example.com"));
    }

    @Test
    void testFetchContactInvalidPhoneNumber() throws Exception {
        mockMvc.perform(get("/api/v1/contact").param(invalidPhoneNumber))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateContact() throws Exception {
        doNothing().when(contactService).createContact(any(ContactDto.class));
        mockMvc.perform(post("/api/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(ContactConstants.STATUS_201))
                .andExpect(jsonPath("$.statusMsg").value(ContactConstants.MESSAGE_201));
    }

    @Test
    public void testUpdateContact_Success() throws Exception {
        when(contactService.updateContact(any(ContactDto.class))).thenReturn(true);
        mockMvc.perform(put("/api/v1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(ContactConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(ContactConstants.MESSAGE_200));
    }

    @Test
    public void testUpdateContact_Failure() throws Exception {

        when(contactService.updateContact(any(ContactDto.class))).thenReturn(false);
        mockMvc.perform(put("/api/v1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto)))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode").value(ContactConstants.STATUS_417))
                .andExpect(jsonPath("$.statusMsg").value(ContactConstants.MESSAGE_417_UPDATE));
    }

    @Test
    public void testDeleteContact_Success() throws Exception {
        when(contactService.deleteContact(any(String.class))).thenReturn(true);
        mockMvc.perform(delete("/api/v1/delete").param("phoneNumber", validPhoneNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(ContactConstants.STATUS_200))
                .andExpect(jsonPath("$.statusMsg").value(ContactConstants.MESSAGE_200));
    }

    @Test
    public void testDeleteContactWithInvalidPhoneNumber_Failure() throws Exception {
        when(contactService.deleteContact(any(String.class))).thenReturn(false);
        mockMvc.perform(delete("/api/v1/delete").param("phoneNumber", invalidPhoneNumber))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.errorMessage").value("Phone number must be 10 digits"));
    }

    @Test
    public void testDeleteContactWithValidPhoneNumber_Failure() throws Exception {
        when(contactService.deleteContact(any(String.class))).thenReturn(false);
        mockMvc.perform(delete("/api/v1/delete").param("phoneNumber", validPhoneNumber))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode").value(ContactConstants.STATUS_417))
                .andExpect(jsonPath("$.statusMsg").value(ContactConstants.MESSAGE_417_DELETE));
    }


}
