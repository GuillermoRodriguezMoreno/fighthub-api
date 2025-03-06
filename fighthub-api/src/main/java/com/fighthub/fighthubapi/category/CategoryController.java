package com.fighthub.fighthubapi.category;

import com.fighthub.fighthubapi.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Categories")
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Long> saveCategory(
            @Valid @RequestBody CategoryRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(categoryService.saveCategory(request));
    }
    @GetMapping("{category-id}")
    public ResponseEntity<CategoryResponse> findCategoryById(
            @PathVariable("category-id") Long categoryId
    ) {
        return ResponseEntity.ok(categoryService.findCategoryById(categoryId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> findAllCategories(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "name" ,required = false) String orderBy
    ){
        return ResponseEntity.ok(categoryService.findAllCategories(page, size, orderBy));
    }
    @PutMapping("{category-id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable("category-id") Long categoryId,
            @Valid @RequestBody CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, request));
    }
    @DeleteMapping("{category-id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("category-id") Long categoryId
    ) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
