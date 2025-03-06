package com.fighthub.fighthubapi.unit;

import com.fighthub.fighthubapi.location.Location;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LocationTest {

    @Test
    void testDistanceBetweenTwoLocations() {

        final double EARTH_RADIUS = 6378;

        double lat1 = 48.856613;
        double lon1 = 2.352222;

        double lat2 = 41.385063;
        double lon2 = 2.173404;

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dlat = lat2Rad - lat1Rad;
        double dlon = lon2Rad - lon1Rad;

        double sinLat = Math.sin(dlat / 2);
        double sinLon = Math.sin(dlon / 2);

        double a = sinLat * sinLat + Math.cos(lat1Rad) * Math.cos(lat2Rad) * sinLon * sinLon;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        BigDecimal expectedDistance = BigDecimal.valueOf(EARTH_RADIUS * c).setScale(3, RoundingMode.HALF_EVEN);

        Location location1 = Location.builder()
                .latitude(BigDecimal.valueOf(lat1))
                .longitude(BigDecimal.valueOf(lon1))
                .build();

        Location location2 = Location.builder()
                .latitude(BigDecimal.valueOf(lat2))
                .longitude(BigDecimal.valueOf(lon2))
                .build();

        BigDecimal distance = location1.distance(location2);

        assertEquals(expectedDistance, distance);
    }
}
