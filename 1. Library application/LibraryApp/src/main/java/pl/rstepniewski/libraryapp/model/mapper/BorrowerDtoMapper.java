package pl.rstepniewski.libraryapp.model.mapper;

import org.springframework.stereotype.Component;
import pl.rstepniewski.libraryapp.model.Borrower;
import pl.rstepniewski.libraryapp.model.dto.BorrowerDTO;

@Component
public class BorrowerDtoMapper {

    public BorrowerDTO map(Borrower borrower) {
        BorrowerDTO dto = BorrowerDTO.builder()
                .id(borrower.getId())
                .pesel(borrower.getPesel())
                .name(borrower.getName())
                .lastName(borrower.getLastName())
                .build();

        return dto;
    }

    public Borrower map(BorrowerDTO dto) {
        Borrower borrower = Borrower.builder()
                .id(dto.getId())
                .pesel(dto.getPesel())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .build();

        return borrower;
    }
}
