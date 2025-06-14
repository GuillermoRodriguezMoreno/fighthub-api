package com.fighthub.fighthubapi.fighting_matchmaking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpponentRank {
        private Long id;
        private String name;
        private double score;
}