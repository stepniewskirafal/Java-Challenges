package pl.rstepniewski.libraryapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pl.rstepniewski.libraryapp.model.dto.LibraryBranchDTO;
import pl.rstepniewski.libraryapp.services.LibraryBranchService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryBranchController.class)
public class LibraryBranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryBranchService libraryBranchService;

    @Autowired
    private ObjectMapper objectMapper;

    private LibraryBranchDTO libraryBranchDTO;

    @BeforeEach
    void setUp() {
        libraryBranchDTO = LibraryBranchDTO.builder()
                .id(UUID.fromString("d85f8fd5-a2b3-4aef-8a58-a3b3c5a1b3c4"))
                .name("Central Library")
                .address("123 Library Street2")
                .build();
    }

    @Test
    void shouldAddLibraryBranch() throws Exception {
        when(libraryBranchService.save(any(LibraryBranchDTO.class))).thenReturn(libraryBranchDTO);

        mockMvc.perform(post("/libre-app/v1.0/libraryBranch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libraryBranchDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(libraryBranchDTO.getId().toString()))
                .andExpect(jsonPath("$.name").value("Central Library"))
                .andExpect(jsonPath("$.address").value("123 Library Street2"));
    }

    @Test
    void shouldDeleteLibraryBranch() throws Exception {
        UUID libraryBranchId = libraryBranchDTO.getId();

        mockMvc.perform(delete("/libre-app/v1.0/libraryBranch/{libraryBranchId}", libraryBranchId))
                .andExpect(status().isNoContent());

        BDDMockito.verify(libraryBranchService).deleteById(libraryBranchId);
    }
}
