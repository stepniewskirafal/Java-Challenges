package pl.rstepniewski.libraryapp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.libraryapp.validation.exceptions.BookNotFoundException;
import pl.rstepniewski.libraryapp.model.Book;
import pl.rstepniewski.libraryapp.model.dto.BookDTO;
import pl.rstepniewski.libraryapp.model.mapper.BookDtoMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.rstepniewski.libraryapp.repository.BookRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;

    public BookService(BookRepository bookRepository, BookDtoMapper bookDtoMapper) {
        this.bookRepository = bookRepository;
        this.bookDtoMapper = bookDtoMapper;
    }

    @Transactional(readOnly = true)
    public Optional<BookDTO> getBookById(UUID id) {
        return bookRepository.findById(id).map(bookDtoMapper::map);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Book> booksPaged = bookRepository.findAll(pageable);
        List<Book> booksPage = booksPaged.getContent();
        List<BookDTO> bookDTOS = booksPage.stream()
                .map(bookDtoMapper::map)
                .collect(Collectors.toList());
        return bookDTOS;
    }

    @Transactional
    public BookDTO saveBook(BookDTO dto) {
        Book book = bookDtoMapper.map(dto);
        Book savedBook = bookRepository.save(book);
        return bookDtoMapper.map(savedBook);
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }


    @Transactional
    public Optional<BookDTO> replaceBook(UUID id, BookDTO bookDTO) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookDTO.setId(id);
        Book book = bookDtoMapper.map(bookDTO);
        Book savedBook = bookRepository.save(book);
        return Optional.of(bookDtoMapper.map(savedBook));
    }

    @Transactional
    public void updateBook(BookDTO dto) {
        Book book = bookDtoMapper.map(dto);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    public Optional<BookDTO> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).map(bookDtoMapper::map);
    }

    public boolean doesExist(UUID id) {
        return bookRepository.existsById(id);
    }
}
