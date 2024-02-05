package pl.rstepniewski.libraryapp.model.mapper;

import org.springframework.stereotype.Component;
import pl.rstepniewski.libraryapp.model.Category;
import pl.rstepniewski.libraryapp.model.dto.CategoryDTO;

@Component
public class CategoryDtoMapper {

    public CategoryDTO map(Category category) {
        CategoryDTO dto = CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
        return dto;
    }

    public Category map(CategoryDTO dto) {
        Category category = Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        return category;
    }
}
