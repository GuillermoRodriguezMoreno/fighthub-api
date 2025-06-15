package com.fighthub.fighthubapi.picture;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// TODO: Implement this
public class PictureResponse {
    private long id;
    private long fighterProfileId;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastUpdatedBy;
}
