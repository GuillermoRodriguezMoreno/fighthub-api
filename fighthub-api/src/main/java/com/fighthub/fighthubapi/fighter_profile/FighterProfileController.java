package com.fighthub.fighthubapi.fighter_profile;

import com.fighthub.fighthubapi.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Tag(name = "FighterProfiles")
@RequestMapping("/fighter-profiles")
public class FighterProfileController {

    private final FighterProfileService fighterProfileService;
    private final FighterProfileRepository fighterProfileRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping
    public ResponseEntity<Long> saveFighterProfile(
            @Valid @RequestBody FighterProfileRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(fighterProfileService.saveFighterProfile(request));
    }
    @GetMapping("{fighterProfile-id}")
    public ResponseEntity<FighterProfileResponse> findFighterProfileById(
            @PathVariable("fighterProfile-id") Long fighterProfileId
    ) {
        return ResponseEntity.ok(fighterProfileService.findFighterProfileById(fighterProfileId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<FighterProfileResponse>> findAllFighterProfiles(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "firstname", required = false) String orderBy
    ){
        return ResponseEntity.ok(fighterProfileService.findAllFighterProfiles(page, size, orderBy));
    }
    @PutMapping("{fighterProfile-id}")
    public ResponseEntity<FighterProfileResponse> updateFighterProfile(
            @PathVariable("fighterProfile-id") Long fighterProfileId,
            @Valid @RequestBody FighterProfileRequest request
    ) {
        return ResponseEntity.ok(fighterProfileService.updateFighterProfile(fighterProfileId, request));
    }
    @DeleteMapping("{fighterProfile-id}")
    public ResponseEntity<Void> deleteFighterProfile(
            @PathVariable("fighterProfile-id") Long fighterProfileId
    ) {
        fighterProfileService.deleteFighterProfile(fighterProfileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/club/{club-id}")
    public ResponseEntity<PageResponse<FighterProfileResponse>> findFighterProfilesByClub(
            @PathVariable("club-id") Long clubId,
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(name = "orderBy", defaultValue = "firstname", required = false) String orderBy
    ) {
        return ResponseEntity.ok(fighterProfileService.findAllFighterProfilesByClubId(clubId, page, size, orderBy));
    }

    @GetMapping("/{fighter_id}/nearest")
    public ResponseEntity<Set<FighterProfileResponse>> findNearestFighterProfiles(
            @PathVariable("fighter_id") Long fighterId,
            @RequestParam(name = "radius", defaultValue = "50", required = false) Long radius
    ) {
        return ResponseEntity.ok(fighterProfileService.findAllFighterNearestToLocation(fighterId, radius));
    }

    @PostMapping("/{fighter_id}/upload")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long fighter_id, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = fighterProfileService.uploadProfilePicture(fighter_id, file);
            return ResponseEntity.ok("Image uploaded successful: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/profile-picture/{filename}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
