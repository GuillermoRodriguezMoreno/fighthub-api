package com.fighthub.fighthubapi.picture;

import com.fighthub.fighthubapi.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository PictureRepository;
    private final PictureMapper PictureMapper;
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
}
