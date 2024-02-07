package pl.rstepniewski.libraryapp.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.rstepniewski.libraryapp.model.Book;
import pl.rstepniewski.libraryapp.model.dto.BookDTO;

@Component
@RequiredArgsConstructor
public class BookDtoMapper {

    private final CategoryDtoMapper categoryDtoMapper;
    private final LibraryBranchDtoMapper libraryBranchDtoMapper;


    public BookDTO map(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .yearPublished(book.getYearPublished())
                .isbn(book.getIsbn())
                .isAvailable(book.isAvailable())
                .category(categoryDtoMapper.map(book.getCategory()))
                .libraryBranch(libraryBranchDtoMapper.map(book.getLibraryBranch()))
                .build();
    }

    public Book map(BookDTO dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .yearPublished(dto.getYearPublished())
                .isbn(dto.getIsbn())
                .isAvailable(dto.isAvailable())
                .category(categoryDtoMapper.map(dto.getCategory()))
                .libraryBranch(libraryBranchDtoMapper.map(dto.getLibraryBranch()))
                .build();
    }
}
