package pl.rstepniewski.libraryapp.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.libraryapp.validation.exceptions.BookNotFoundException;
import pl.rstepniewski.libraryapp.model.*;
import pl.rstepniewski.libraryapp.model.dto.LendingLogDTO;
import pl.rstepniewski.libraryapp.model.mapper.LendingLogDtoMapper;
import pl.rstepniewski.libraryapp.repository.LendingLogRepository;
import pl.rstepniewski.libraryapp.validation.exceptions.BorrowerNotFoundException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LendingLogService {

    private final LendingLogRepository lendingLogRepository;
    private final BookService bookService;
    private final BorrowerService borrowerService;
    private final LendingLogDtoMapper lendingLogDtoMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public LendingLogDTO save(LendingLogDTO lendingLogDTO) {
        final LendingLog lendingLog = lendingLogDtoMapper.map(lendingLogDTO);

        if(!borrowerService.doesExist(lendingLog.getBorrower().getId())){
            throw new BorrowerNotFoundException(lendingLog.getBorrower().getId());
        }

        Set<Book> books = lendingLog.getBooks();
        final Set<Book> updatedBooks = updateAvailableState(books, lendingLog.isReturn());
        lendingLog.setBooks(updatedBooks);

        final LendingLog savedLendingLog = lendingLogRepository.save(lendingLog);
        return lendingLogDtoMapper.map(savedLendingLog);
    }

    @Transactional(propagation = Propagation.REQUIRED )
    public Set<Book> updateAvailableState(Set<Book> books, boolean isReturn) {
        for( Book book: books){
            if( !bookService.doesExist(book.getId()) ){
                throw new BookNotFoundException(book.getIsbn());
            }
            book.setAvailable(isReturn);
        }
        bookService.saveAllBooks(books);
        return books;
    }
}
