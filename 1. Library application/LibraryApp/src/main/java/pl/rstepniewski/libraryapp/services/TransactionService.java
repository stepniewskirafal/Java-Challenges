package pl.rstepniewski.libraryapp.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.libraryapp.validation.exceptions.BookNotFoundException;
import pl.rstepniewski.libraryapp.model.*;
import pl.rstepniewski.libraryapp.model.dto.TransactionDTO;
import pl.rstepniewski.libraryapp.model.mapper.TransactionDtoMapper;
import pl.rstepniewski.libraryapp.repository.TransactionRepository;
import pl.rstepniewski.libraryapp.validation.exceptions.BorrowerNotFoundException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BookService bookService;
    private final BorrowerService borrowerService;
    private final TransactionDtoMapper transactionDtoMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionDTO save(TransactionDTO transactionDTO) {
        final Transaction transaction = transactionDtoMapper.map(transactionDTO);
        Set<Book> books = transaction.getBooks();
        final Set<Book> updatedBooks = updateAvailableState(books, transaction.isReturn());
        transaction.setBooks(updatedBooks);

        if(!borrowerService.doesExist(transaction.getBorrower().getId())){
            throw new BorrowerNotFoundException(transaction.getBorrower().getId());
        }

        final Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionDtoMapper.map(savedTransaction);
    }

    @Transactional(propagation = Propagation.REQUIRED )
    public Set<Book> updateAvailableState(Set<Book> books, boolean isReturn) {
        Set<Book> booksResult = books;
        for( Book book: booksResult){
            if( !bookService.doesExist(book.getId()) ){
                throw new BookNotFoundException(book.getIsbn());
            }
            book.setAvailable(isReturn);
            bookService.saveBook(book);
        }
        return booksResult;
    }
}
