package pl.rstepniewski.libraryapp.model.mapper;

import org.springframework.stereotype.Component;
import pl.rstepniewski.libraryapp.model.LendingLog;
import pl.rstepniewski.libraryapp.model.dto.LendingLogDTO;

@Component
public class LendingLogDtoMapper {
    public LendingLogDTO map(LendingLog lendingLog) {
        LendingLogDTO dto = LendingLogDTO.builder()
                .id(lendingLog.getId())
                .transactionDate(lendingLog.getTransactionDate())
                .isReturn(lendingLog.isReturn())
                .borrower(lendingLog.getBorrower())
                .books(lendingLog.getBooks())
                .build();
        return dto;
    }

    public LendingLog map(LendingLogDTO dto) {
        LendingLog lendingLog = LendingLog.builder()
                .id(dto.getId())
                .transactionDate(dto.getTransactionDate())
                .isReturn(dto.isReturn())
                .borrower(dto.getBorrower())
                .books(dto.getBooks())
                .build();
        return lendingLog;
    }
}
