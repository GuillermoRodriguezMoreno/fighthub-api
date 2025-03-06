package com.fighthub.fighthubapi.location;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime timestamp = LocalDateTime.now();

    public BigDecimal distance(Location otherLocation) {
        final BigDecimal EARTH_RADIUS = new BigDecimal("6378");
        BigDecimal latDist = BigDecimal.valueOf(Math.toRadians(otherLocation.latitude.subtract(this.latitude).doubleValue()));
        BigDecimal lonDist = BigDecimal.valueOf(Math.toRadians(otherLocation.longitude.subtract(this.longitude).doubleValue()));

        BigDecimal sinLat = BigDecimal.valueOf(Math.sin(latDist.divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN).doubleValue()));
        BigDecimal sinLon = BigDecimal.valueOf(Math.sin(lonDist.divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN).doubleValue()));

        BigDecimal a = sinLat.multiply(sinLat)
                .add(BigDecimal.valueOf(Math.cos(Math.toRadians(this.latitude.doubleValue())))
                        .multiply(BigDecimal.valueOf(Math.cos(Math.toRadians(otherLocation.latitude.doubleValue()))))
                        .multiply(sinLon).multiply(sinLon));

        BigDecimal c = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(Math.atan2(Math.sqrt(a.doubleValue()), Math.sqrt(1 - a.doubleValue()))));

        return EARTH_RADIUS.multiply(c).setScale(2, RoundingMode.HALF_UP);
    }
}
