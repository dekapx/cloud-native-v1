package com.dekapx.weather.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReading {
    private String sensorId;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private LocalDateTime timestamp;
}
