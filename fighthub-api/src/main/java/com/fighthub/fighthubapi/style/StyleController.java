package com.fighthub.fighthubapi.style;

import com.fighthub.fighthubapi.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Styles")
@RequestMapping("/styles")
public class StyleController {

    private final StyleService styleService;

    @PostMapping
    public ResponseEntity<Long> saveStyle(
            @Valid @RequestBody StyleRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(styleService.saveStyle(request));
    }
    @GetMapping("{style-id}")
    public ResponseEntity<StyleResponse> findStyleById(
            @PathVariable("style-id") Long styleId
    ) {
        return ResponseEntity.ok(styleService.findStyleById(styleId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<StyleResponse>> findAllStyles(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "50", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "name", required = false) String orderBy
    ){
        return ResponseEntity.ok(styleService.findAllStyles(page, size, orderBy));
    }
    @PutMapping("{style-id}")
    public ResponseEntity<StyleResponse> updateStyle(
            @PathVariable("style-id") Long styleId,
            @Valid @RequestBody StyleRequest request
    ) {
        return ResponseEntity.ok(styleService.updateStyle(styleId, request));
    }
    @DeleteMapping("{style-id}")
    public ResponseEntity<Void> deleteStyle(
            @PathVariable("style-id") Long styleId
    ) {
        styleService.deleteStyle(styleId);
        return ResponseEntity.noContent().build();
    }
}
