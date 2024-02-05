package pl.rstepniewski.libraryapp.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import pl.rstepniewski.libraryapp.validation.exceptions.CategoryBranchAlreadyExistsException;
import pl.rstepniewski.libraryapp.model.Category;
import pl.rstepniewski.libraryapp.model.mapper.CategoryDtoMapper;
import pl.rstepniewski.libraryapp.model.dto.CategoryDTO;
import pl.rstepniewski.libraryapp.repository.CategoryRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategorySerice {

    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper categoryDtoMapper;

    public CategorySerice(CategoryRepository categoryRepository, CategoryDtoMapper categoryDtoMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryDtoMapper = categoryDtoMapper;
    }

    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findById(UUID categoryid) {
        return categoryRepository.findById(categoryid).map(categoryDtoMapper::map);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        categoryRepository.findByNameEqualsIgnoreCase(categoryDTO.getName())
                .ifPresent(branch -> {
                    throw new CategoryBranchAlreadyExistsException(categoryDTO.getName());
                });

        final Category category = categoryDtoMapper.map(categoryDTO);
        final Category savedCategory = categoryRepository.save(category);
        return categoryDtoMapper.map(savedCategory);
    }

    @Transactional
    public void deleteById(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
