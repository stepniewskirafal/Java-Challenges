package pl.rstepniewski.libraryapp.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import pl.rstepniewski.libraryapp.validation.exceptions.LibraryBranchAlreadyExistsException;
import pl.rstepniewski.libraryapp.model.LibraryBranch;
import pl.rstepniewski.libraryapp.model.mapper.LibraryBranchDtoMapper;
import pl.rstepniewski.libraryapp.model.dto.LibraryBranchDTO;
import pl.rstepniewski.libraryapp.repository.LibraryBranchRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LibraryBranchService {
    private final LibraryBranchRepository libraryBranchRepository;
    private final LibraryBranchDtoMapper libraryBranchDtoMapper;

    public LibraryBranchService(LibraryBranchRepository libraryBranchRepository, LibraryBranchDtoMapper libraryBranchDtoMapper) {
        this.libraryBranchRepository = libraryBranchRepository;
        this.libraryBranchDtoMapper = libraryBranchDtoMapper;
    }

    @Transactional(readOnly = true)
    public List<LibraryBranchDTO> getAll() {
        return libraryBranchRepository
                .findAll()
                .stream()
                .map(libraryBranchDtoMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public LibraryBranchDTO save(LibraryBranchDTO libraryBranchDTO) {
        libraryBranchRepository.findByNameEqualsIgnoreCase(libraryBranchDTO.getName())
                .ifPresent(branch -> {
                    throw new LibraryBranchAlreadyExistsException(libraryBranchDTO.getName());
                });
        libraryBranchRepository.findByAddressEqualsIgnoreCase(libraryBranchDTO.getAddress())
                .ifPresent(branch -> {
                    throw new LibraryBranchAlreadyExistsException(libraryBranchDTO.getAddress());
                });
        final LibraryBranch libraryBranch = libraryBranchDtoMapper.map(libraryBranchDTO);
        final LibraryBranch savedLibraryBranch = libraryBranchRepository.save(libraryBranch);
        return libraryBranchDtoMapper.map(savedLibraryBranch);
    }

    @Transactional
    public void deleteById(UUID libraryBranchId) {
        libraryBranchRepository.deleteById(libraryBranchId);
    }
}
