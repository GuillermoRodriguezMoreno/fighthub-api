package com.fighthub.fighthubapi.picture;

import com.fighthub.fighthubapi.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Tag(name = "Pictures")
@RequestMapping("/pictures")
public class PictureController {

    private final PictureService pictureService;

    @PostMapping
    public ResponseEntity<Long> savePicture(
            @Valid @RequestBody PictureRequest request) {
        return ResponseEntity.ok(pictureService.savePicture(request));
    }
    @GetMapping("{picture-id}")
    public ResponseEntity<PictureResponse> findPictureById(
            @PathVariable("picture-id") Long PictureId
    ) {
        return ResponseEntity.ok(pictureService.findPictureById(PictureId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<PictureResponse>> findAllPictures(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "id", required = false) String orderBy
    ){
        return ResponseEntity.ok(pictureService.findAllPictures(page, size, orderBy));
    }
    @PutMapping("{picture-id}")
    public ResponseEntity<PictureResponse> updatePicture(
            @PathVariable("picture-id") Long pictureId,
            @Valid @RequestBody PictureRequest request
    ) {
        return ResponseEntity.ok(pictureService.updatePicture(pictureId, request));
    }
    @DeleteMapping("{picture-id}")
    public ResponseEntity<Void> deletePicture(
            @PathVariable("picture-id") Long pictureId
    ) {
        pictureService.deletePicture(pictureId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fighter-profile/{fighter-profile-id}")
    public ResponseEntity<Set<PictureResponse>> findAllPicturesByFighterProfileId(
            @PathVariable("fighter-profile-id") Long fighterProfileId
    ) {
        return ResponseEntity.ok(pictureService.findAllPicturesByFighterProfileId(fighterProfileId));
    }
}
