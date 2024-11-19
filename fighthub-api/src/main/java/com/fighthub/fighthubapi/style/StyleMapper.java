package com.fighthub.fighthubapi.style;

import com.fighthub.fighthubapi.style.Style;
import com.fighthub.fighthubapi.style.StyleRequest;
import com.fighthub.fighthubapi.style.StyleResponse;
import org.springframework.stereotype.Service;

@Service
public class StyleMapper {

    public Style toStyle(StyleRequest request) {
        return Style.builder()
                .id(request.id())
                .name(request.name())
                .build();
    }

    public StyleResponse toStyleResponse(Style style) {
        return StyleResponse.builder()
                .id(style.getId())
                .name(style.getName())
                .build();
    }
}
