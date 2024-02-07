package pl.rstepniewski.libraryapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.rstepniewski.libraryapp.model.dto.BorrowerDTO;
import pl.rstepniewski.libraryapp.services.BorrowerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BorrowerController.class)
public class BorrowerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowerService borrowerService;

    @Autowired
    private ObjectMapper objectMapper;

    private BorrowerDTO borrowerDTO;

    @BeforeEach
    void setUp() {
        borrowerDTO = BorrowerDTO.builder()
                .id(UUID.randomUUID())
                .pesel(12345678901L)
                .name("John")
                .lastName("Doe")
                .build();
    }

    @Test
    void shouldGetBorrowers() throws Exception {
        List<BorrowerDTO> borrowerDTOList = Arrays.asList(borrowerDTO);
        when(borrowerService.getBorrowers()).thenReturn(borrowerDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/libre-app/v1.0/borrowers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(borrowerDTO.getName()));
    }

    @Test
    void shouldGetBorrower() throws Exception {
        when(borrowerService.getBorrowerById(any(UUID.class))).thenReturn(Optional.of(borrowerDTO));

        mockMvc.perform(get("/libre-app/v1.0/borrower/{borrowerId}", borrowerDTO.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(borrowerDTO.getName()));
    }

    @Test
    void shouldCreateBorrower() throws Exception {
        when(borrowerService.save(any(BorrowerDTO.class))).thenReturn(borrowerDTO);

        mockMvc.perform(post("/libre-app/v1.0/borrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowerDTO)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value(borrowerDTO.getName()));
    }
}
