package com.fighthub.fighthubapi.picture;

import com.fighthub.fighthubapi.role.Role;
import com.fighthub.fighthubapi.role.RoleRequest;
import com.fighthub.fighthubapi.role.RoleResponse;
import org.springframework.stereotype.Service;

@Service
public class PictureMapper {

    public Picture toPicture(PictureRequest request) {
        return Picture.builder()
                .id(request.id())
                .url(request.url())
                .fighterProfile(request.fighterProfile())
                .build();
    }

    public PictureResponse toPictureResponse(Picture picture) {
        return PictureResponse.builder()
                .id(picture.getId())
                .fighterProfileId(picture.getFighterProfile().getId())
                .url(picture.getUrl())
                .createdAt(picture.getCreatedAt())
                .updatedAt(picture.getUpdatedAt())
                .createdBy(picture.getCreatedBy())
                .lastUpdatedBy(picture.getLastUpdatedBy())
                .build();
    }
}
