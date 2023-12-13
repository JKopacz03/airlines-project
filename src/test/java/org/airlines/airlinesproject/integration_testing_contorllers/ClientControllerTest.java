package org.airlines.airlinesproject.integration_testing_contorllers;

import lombok.SneakyThrows;
import org.airlines.airlinesproject.client.ClientController;
import org.airlines.airlinesproject.client.ClientService;
import org.airlines.airlinesproject.client.dto.ClientNewPasswordRequest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @MockBean
    private ClientService clientService;
    private ClientController clientController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @SneakyThrows
    @WithMockUser(username="spring")
    @Test
    public void findAllClients_findingAllClients_returnsOk() {

        this.mockMvc.perform(get("/api/v1/find-all-clients")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

//         this.mockMvc.perform(patch("/api/v1/modify/password")
//                        .content("{\"email\": \"" + email + "\"," +
//            "\"currentPassword\": \"" + currentPassword + "\"," +
//            "\"newPassword\": \"" + newPassword + "\"," +
//            "}")
//                        .contentType(MediaType.APPLICATION_JSON_UTF8));

}
