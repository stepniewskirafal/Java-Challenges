package pl.rstepniewski.libraryapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pl.rstepniewski.libraryapp.model.dto.*;
import pl.rstepniewski.libraryapp.services.LendingLogService;

import java.time.Instant;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LendingLogController.class)
public class LendingLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LendingLogService lendingLogService;

    @Autowired
    private ObjectMapper objectMapper;

    private LendingLogDTO lendingLogDTO;

    @BeforeEach
    void setUp() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(UUID.fromString("d85f8fd5-a2b3-4aef-8a58-a3b3c5a1b3c4"))
                .name("Fiction1")
                .description("Fictional books and novels2")
                .build();

        LibraryBranchDTO libraryBranchDTO = LibraryBranchDTO.builder()
                .id(UUID.fromString("d85f8fd5-a2b3-4aef-8a58-a3b3c5a1b3c4"))
                .name("Central Library")
                .address("123 Library Street2")
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .id(UUID.fromString("d85f8fd5-a2b3-4aef-8a58-a3b3c5a1b3c4"))
                .isAvailable(true)
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .yearPublished(1925)
                .isbn("978-0-7432-7356-5")
                .category(categoryDTO)
                .libraryBranch(libraryBranchDTO)
                .build();

        BorrowerDTO borrowerDTO = BorrowerDTO.builder()
                .id(UUID.randomUUID())
                .pesel(12345678901L)
                .name("John")
                .lastName("Doe")
                .build();

        lendingLogDTO = LendingLogDTO.builder()
                .id(UUID.fromString("d85f8fd5-a2b3-4aef-8a58-a3b3c5a1b3c4"))
                .transactionDate(Date.from(Instant.now()))
                .isReturn(false)
                .books(Set.of(bookDTO))
                .borrower(borrowerDTO)
                .build();
    }

    @Test
    void shouldGetLog() throws Exception {
        when(lendingLogService.getAll(anyInt(), anyInt())).thenReturn(Collections.singletonList(lendingLogDTO));

        mockMvc.perform(get("/libre-app/v1.0/lending-logs?pageNo=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(lendingLogDTO.getId().toString()));
    }

    @Test
    void shouldCreateLog() throws Exception {
        when(lendingLogService.save(any(LendingLogDTO.class))).thenReturn(lendingLogDTO);

        mockMvc.perform(post("/libre-app/v1.0/lending-log")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lendingLogDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(lendingLogDTO.getId().toString()));
    }
}
