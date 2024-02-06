package pl.rstepniewski.libraryapp.model.mapper;

import org.springframework.stereotype.Component;
import pl.rstepniewski.libraryapp.model.LendingLog;
import pl.rstepniewski.libraryapp.model.dto.LendingLogDTO;

@Component
public class LendingLogDtoMapper {
    public LendingLogDTO map(LendingLog lendingLog) {
        return LendingLogDTO.builder()
                .id(lendingLog.getId())
                .transactionDate(lendingLog.getTransactionDate())
                .isReturn(lendingLog.isReturn())
                .borrower(lendingLog.getBorrower())
                .books(lendingLog.getBooks())
                .build();
    }

    public LendingLog map(LendingLogDTO dto) {
        return LendingLog.builder()
                .id(dto.getId())
                .transactionDate(dto.getTransactionDate())
                .isReturn(dto.isReturn())
                .borrower(dto.getBorrower())
                .books(dto.getBooks())
                .build();
    }
}
