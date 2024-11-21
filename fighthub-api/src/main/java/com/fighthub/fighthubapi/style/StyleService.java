package com.fighthub.fighthubapi.style;

import com.fighthub.fighthubapi.style.*;
import com.fighthub.fighthubapi.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StyleService {

    private final StyleRepository styleRepository;
    private final StyleMapper styleMapper;
    public Long saveStyle(StyleRequest request) {
        Style style = styleMapper.toStyle(request);
        return styleRepository.save(style).getId();
    }

    public StyleResponse findStyleById(Long styleId) {
        return styleRepository.findById(styleId)
                .map(styleMapper::toStyleResponse)
                .orElseThrow(() -> new EntityNotFoundException("Style not found with id: " + styleId));
    }

    public PageResponse<StyleResponse> findAllStyles(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        Page<Style> styles = styleRepository.findAll(pageable);
        List<StyleResponse> styleResponse = styles.stream()
                .map(styleMapper::toStyleResponse)
                .toList();
        return new PageResponse<>(
                styleResponse,
                styles.getNumber(),
                styles.getSize(),
                styles.getTotalElements(),
                styles.getTotalPages(),
                styles.isFirst(),
                styles.isLast());
    }
    public StyleResponse updateStyle(Long styleId, StyleRequest request) {
        Style style = styleRepository.findById(styleId)
                .orElseThrow(() -> new EntityNotFoundException("Style not found with id: " + styleId));
        style.setName(request.name());
        return styleMapper.toStyleResponse(styleRepository.save(style));
    }
    @Transactional
    public void deleteStyle(Long styleId) {
        styleRepository.deleteStyleAssociations(styleId);
        styleRepository.deleteById(styleId);
    }
}
