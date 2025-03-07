package com.fighthub.fighthubapi.picture;

import com.fighthub.fighthubapi.common.PageResponse;
import com.fighthub.fighthubapi.fighter_profile.FighterProfile;
import com.fighthub.fighthubapi.fighter_profile.FighterProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final FighterProfileRepository fighterProfileRepository;
    private final PictureRepository PictureRepository;
    private final PictureMapper PictureMapper;

    @Value("${upload.path}")
    private String uploadPath;

    public Long savePicture(PictureRequest request) {
        Picture Picture = PictureMapper.toPicture(request);
        return PictureRepository.save(Picture).getId();
    }

    public PictureResponse findPictureById(Long PictureId) {
        return PictureRepository.findById(PictureId)
                .map(PictureMapper::toPictureResponse)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found with id: " + PictureId));
    }

    public PageResponse<PictureResponse> findAllPictures(Integer page, Integer size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        Page<Picture> Pictures = PictureRepository.findAll(pageable);
        List<PictureResponse> PictureResponse = Pictures.stream()
                .map(PictureMapper::toPictureResponse)
                .toList();
        return new PageResponse<>(
                PictureResponse,
                Pictures.getNumber(),
                Pictures.getSize(),
                Pictures.getTotalElements(),
                Pictures.getTotalPages(),
                Pictures.isFirst(),
                Pictures.isLast());
    }
    public PictureResponse updatePicture(Long PictureId, PictureRequest request) {
        Picture Picture = PictureRepository.findById(PictureId)
                .orElseThrow(() -> new EntityNotFoundException("Picture not found with id: " + PictureId));
        Picture.setUrl(request.url());
        Picture.setFighterProfile(request.fighterProfile());
        return PictureMapper.toPictureResponse(PictureRepository.save(Picture));
    }
    public void deletePicture(Long PictureId) {
        PictureRepository.deleteById(PictureId);
    }

    public Set<PictureResponse> findAllPicturesByFighterProfileId(Long fighterProfileId) {
        return PictureRepository.findAllByFighterProfileId(fighterProfileId)
                .stream()
                .map(PictureMapper::toPictureResponse)
                .collect(Collectors.toSet());
    }

    public List<String> uploadFighterProfilePictures(Long fighterProfileId, List<MultipartFile> files) throws IOException {
        FighterProfile fighterProfile = fighterProfileRepository.findById(fighterProfileId)
                .orElseThrow(() -> new EntityNotFoundException("fighterProfile not found with id: " + fighterProfileId));

        Path uploadPathDestination = Paths.get(uploadPath);

        if (!Files.exists(uploadPathDestination)) {
            Files.createDirectories(uploadPathDestination);
        }

        List<String> newPicturesUrl = new ArrayList<>();
        Set<Picture> newPictures = new HashSet<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = fighterProfile.getId() + "_gallery_picture_" + file.getOriginalFilename();
                Path filePath = uploadPathDestination.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                newPicturesUrl.add(filePath.toString());
                newPictures.add(Picture.builder()
                        .url(filePath.toString())
                        .fighterProfile(fighterProfile)
                        .build());
            }
        }
        fighterProfile.getPictures().clear();
        fighterProfile.getPictures().addAll(newPictures);
        fighterProfileRepository.save(fighterProfile);

        return newPicturesUrl;
    }
}
