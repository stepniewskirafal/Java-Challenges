package pl.rstepniewski.libraryapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.rstepniewski.libraryapp.model.dto.CategoryDTO;
import pl.rstepniewski.libraryapp.services.CategoryService;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        categoryDTO = CategoryDTO.builder()
                .id(UUID.randomUUID())
                .name("Fiction")
                .description("Fictional books and novels")
                .build();
    }

    @Test
    void shouldGetCategories() throws Exception {
        when(categoryService.findAll()).thenReturn(Collections.singletonList(categoryDTO));

        mockMvc.perform(get("/libre-app/v1.0/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(categoryDTO.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("Fiction"))
                .andExpect(jsonPath("$[0].description").value("Fictional books and novels"));
    }

    @Test
    void shouldGetCategory() throws Exception {
        when(categoryService.findById(categoryDTO.getId())).thenReturn(Optional.of(categoryDTO));

        mockMvc.perform(get("/libre-app/v1.0/category/{categoryId}", categoryDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(categoryDTO.getId().toString()))
                .andExpect(jsonPath("$.name").value("Fiction"))
                .andExpect(jsonPath("$.description").value("Fictional books and novels"));
    }

    @Test
    void shouldCreateCategory() throws Exception {
        when(categoryService.save(any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(post("/libre-app/v1.0/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(categoryDTO.getId().toString()))
                .andExpect(jsonPath("$.name").value("Fiction"))
                .andExpect(jsonPath("$.description").value("Fictional books and novels"));

        verify(categoryService).save(any(CategoryDTO.class));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        mockMvc.perform(delete("/libre-app/v1.0/category/{categoryId}", categoryDTO.getId()))
                .andExpect(status().isNoContent());

        verify(categoryService).deleteById(categoryDTO.getId());
    }
}
