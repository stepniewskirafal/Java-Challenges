package pl.rstepniewski.libraryapp.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.rstepniewski.libraryapp.model.LendingLog;
import pl.rstepniewski.libraryapp.model.dto.LendingLogDTO;

@Component
@RequiredArgsConstructor
public class LendingLogDtoMapper {
    private final BookDtoMapper bookDtoMapper;
    private final BorrowerDtoMapper borrowerDtoMapper;

    public LendingLogDTO map(LendingLog lendingLog) {
        return LendingLogDTO.builder()
                .id(lendingLog.getId())
                .transactionDate(lendingLog.getTransactionDate())
                .isReturn(lendingLog.isReturn())
                .borrower(borrowerDtoMapper.map(lendingLog.getBorrower()))
                .books(bookDtoMapper.mapToDtoList(lendingLog.getBooks()))
                .build();
    }

    public LendingLog map(LendingLogDTO dto) {
        return LendingLog.builder()
                .id(dto.getId())
                .transactionDate(dto.getTransactionDate())
                .isReturn(dto.isReturn())
                .borrower(borrowerDtoMapper.map(dto.getBorrower()))
                .books(bookDtoMapper.mapToList(dto.getBooks()))
                .build();
    }
}
