package com.fighthub.fighthubapi.picture;

import com.fighthub.fighthubapi.fighter_profile.FighterProfile;

public record PictureRequest(
        Long id,
        FighterProfile fighterProfile,
        String url
) {}
