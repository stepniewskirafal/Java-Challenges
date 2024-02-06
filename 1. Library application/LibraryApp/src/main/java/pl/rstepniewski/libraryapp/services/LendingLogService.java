package pl.rstepniewski.libraryapp.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.libraryapp.validation.exceptions.BookNotFoundException;
import pl.rstepniewski.libraryapp.model.*;
import pl.rstepniewski.libraryapp.model.dto.LendingLogDTO;
import pl.rstepniewski.libraryapp.model.mapper.LendingLogDtoMapper;
import pl.rstepniewski.libraryapp.repository.LendingLogRepository;
import pl.rstepniewski.libraryapp.validation.exceptions.BorrowerNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<LendingLogDTO> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        final Page<LendingLog> lendingLogs = lendingLogRepository.findAll(pageable);
        return lendingLogs.stream()
                .map(lendingLogDtoMapper::map)
                .collect(Collectors.toList());
    }
}
