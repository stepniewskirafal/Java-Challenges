package pl.rstepniewski.libraryapp.model.mapper;

import org.springframework.stereotype.Component;
import pl.rstepniewski.libraryapp.model.LibraryBranch;
import pl.rstepniewski.libraryapp.model.Transaction;
import pl.rstepniewski.libraryapp.model.dto.TransactionDTO;

@Component
public class TransactionDtoMapper {
    public TransactionDTO map(Transaction transaction) {
        TransactionDTO dto = TransactionDTO.builder()
                .id(transaction.getId())
                .transactionDate(transaction.getTransactionDate())
                .isReturn(transaction.isReturn())
                .borrower(transaction.getBorrower())
                .books(transaction.getBooks())
                .build();
        return dto;
    }

    public Transaction map(TransactionDTO dto) {
        Transaction transaction = Transaction.builder()
                .id(dto.getId())
                .transactionDate(dto.getTransactionDate())
                .isReturn(dto.isReturn())
                .borrower(dto.getBorrower())
                .books(dto.getBooks())
                .build();
        return transaction;
    }
}
