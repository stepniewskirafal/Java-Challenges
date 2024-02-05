package pl.rstepniewski.libraryapp.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.rstepniewski.libraryapp.validation.exceptions.BorrowerAlreadyExistsException;
import pl.rstepniewski.libraryapp.model.Borrower;
import pl.rstepniewski.libraryapp.model.mapper.BorrowerDtoMapper;
import pl.rstepniewski.libraryapp.model.dto.BorrowerDTO;
import pl.rstepniewski.libraryapp.repository.BorrowerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final BorrowerDtoMapper borrowerDtoMapper;

    public BorrowerService(BorrowerRepository borrowerRepository, BorrowerDtoMapper borrowerDtoMapper) {
        this.borrowerRepository = borrowerRepository;
        this.borrowerDtoMapper = borrowerDtoMapper;
    }

    @Transactional(readOnly = true)
    public List<BorrowerDTO> getBorrowers() {
        return borrowerRepository.findAll()
                .stream()
                .map(borrowerDtoMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<BorrowerDTO> getBorrowerById(UUID borrowerId) {
        return borrowerRepository.findById(borrowerId)
                .map(borrowerDtoMapper::map);
    }
    @Transactional
    public BorrowerDTO save(BorrowerDTO borrowerDTO) {
        borrowerRepository.findByPesel(borrowerDTO.getPesel())
                .ifPresent(branch -> {
                    throw new BorrowerAlreadyExistsException(borrowerDTO.getPesel());
                });

        final Borrower borrower = borrowerDtoMapper.map(borrowerDTO);
        final Borrower savedBorrower = borrowerRepository.save(borrower);
        return borrowerDtoMapper.map(savedBorrower);
    }


    public boolean doesExist(UUID id) {
        return borrowerRepository.existsById(id);
    }
}
