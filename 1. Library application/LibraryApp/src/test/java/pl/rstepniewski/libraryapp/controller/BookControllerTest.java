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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.rstepniewski.libraryapp.model.dto.BookDTO;
import pl.rstepniewski.libraryapp.model.dto.CategoryDTO;
import pl.rstepniewski.libraryapp.model.dto.LibraryBranchDTO;
import pl.rstepniewski.libraryapp.services.BookService;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO bookDTO;
    private UUID bookId;

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

        bookDTO = BookDTO.builder()
                .id(UUID.fromString("d85f8fd5-a2b3-4aef-8a58-a3b3c5a1b3c4"))
                .isAvailable(true)
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .yearPublished(1925)
                .isbn("978-0-7432-7356-5")
                .category(categoryDTO)
                .libraryBranch(libraryBranchDTO)
                .build();

        bookId = bookDTO.getId();
    }

    @Test
    void shouldGetBooks() throws Exception {
        BDDMockito.given(bookService.getAllBooks(0, 10)).willReturn(Collections.singletonList(bookDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/libre-app/v1.0/books?pageNo=0&pageSize=10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(bookDTO.getId().toString()));
    }

    @Test
    void shouldGetBook() throws Exception {
        when(bookService.getBookById(bookId)).thenReturn(Optional.of(bookDTO));

        mockMvc.perform(get("/libre-app/v1.0/book/{id}", bookId.toString()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookDTO.getId().toString()))
                .andExpect(jsonPath("$.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"))
                .andExpect(jsonPath("$.yearPublished").value(1925))
                .andExpect(jsonPath("$.isbn").value("978-0-7432-7356-5"))
                .andExpect(jsonPath("$.category.name").value("Fiction1"))
                .andExpect(jsonPath("$.libraryBranch.name").value("Central Library"));
    }

    @Test
    void shouldCreateBook() throws Exception {
        BDDMockito.given(bookService.saveBook(any(BookDTO.class))).willReturn(bookDTO);

        mockMvc.perform(post("/libre-app/v1.0/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(bookDTO.getId().toString()))
                .andExpect(jsonPath("$.title").value("The Great Gatsby"))
                .andExpect(jsonPath("$.author").value("F. Scott Fitzgerald"))
                .andExpect(jsonPath("$.category.name").value("Fiction1"))
                .andExpect(jsonPath("$.libraryBranch.name").value("Central Library"));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        BDDMockito.given(bookService.replaceBook(eq(bookId), any(BookDTO.class))).willReturn(Optional.of(bookDTO));

        mockMvc.perform(put("/libre-app/v1.0/book/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void shouldPatchBook() throws Exception {
        BDDMockito.given(bookService.getBookById(bookId)).willReturn(Optional.of(bookDTO));

        mockMvc.perform(patch("/libre-app/v1.0/book/{id}", bookId)
                        .contentType("application/merge-patch+json")
                        .content("{\"title\": \"Updated Test Book\"}"))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        BDDMockito.verify(bookService).getBookById(bookId);

        BDDMockito.verify(bookService).updateBook(argThat(book -> "Updated Test Book".equals(book.getTitle())));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/libre-app/v1.0/book/{id}", bookId))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}