package com.thardal.bankapp.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thardal.bankapp.BaseTest;
import com.thardal.bankapp.customer.dto.CustomerSaveRequestDto;
import com.thardal.bankapp.customer.dto.CustomerUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CustomerControllerTest extends BaseTest {
    private static final String BASE_PATH = "/api/customers";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAll() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get(BASE_PATH)
                .content("")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(mvcResult);

        assertTrue(isSuccess);
    }

    @Test
    void shouldSave() throws Exception {

//        String content = "{\n" +
//                "    \"name\": \"test\",\n" +
//                "    \"surname\": \"test\",\n" +
//                "    \"identityNo\": 123453678901,\n" +
//                "    \"password\": \"test\"\n" +
//                "}";

        CustomerSaveRequestDto customerSaveRequestDto = CustomerSaveRequestDto.builder()
                .name("TEEST1")
                .surname("PR2")
                .identityNo(15262342901L)
                .password("test")
                .build();

        String content = objectMapper.writeValueAsString(customerSaveRequestDto);

        MvcResult mvcResult = mockMvc.perform(post(BASE_PATH)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(mvcResult);

        assertTrue(isSuccess);


    }

    @Test
    void shouldFindById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(BASE_PATH + "/2")
                .content("2L")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(mvcResult);

        assertTrue(isSuccess);
    }

    @Test
    void shouldDelete() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(BASE_PATH + "/802")
                .content("802L")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(mvcResult);

        assertTrue(isSuccess);
    }

    @Test
    void shouldUpdate() throws Exception {

        CustomerUpdateRequestDto customerUpdateRequestDto = CustomerUpdateRequestDto.builder()
                .id(852L)
                .name("LETHAL")
                .surname("ROSSH")
                .identityNo(6783456345L)
                .password("A13DF")
                .build();

        String content = objectMapper.writeValueAsString(customerUpdateRequestDto);

        MvcResult mvcResult = mockMvc.perform(put(BASE_PATH)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(mvcResult);

        assertTrue(isSuccess);

    }
}