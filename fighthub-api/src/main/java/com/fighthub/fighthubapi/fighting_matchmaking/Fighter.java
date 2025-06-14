package com.fighthub.fighthubapi.fighting_matchmaking;

import com.fighthub.fighthubapi.location.Location;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fighter {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private double weight;
    private int height;
    private String gender;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer kos;
    private Integer winsInARow;
    private Location location;
    private List<String> styles;
    private String category;
}
