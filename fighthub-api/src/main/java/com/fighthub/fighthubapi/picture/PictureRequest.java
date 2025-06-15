package com.fighthub.fighthubapi.picture;

import com.fighthub.fighthubapi.fighter_profile.FighterProfile;

// TODO: Implement this

public record PictureRequest(
        Long id,
        FighterProfile fighterProfile,
        String url
) {}
