package com.dekapx.weather.service;

import com.dekapx.weather.entity.SensorReading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SensorServiceImpl implements SensorService {

    @Override
    public List<SensorReading> getReadings(final String sensorId) {
        log.info("Fetching sensor reading for sensorId: [{}]", sensorId);
        return List.of(toSensorReading(sensorId));
    }

    @Override
    public List<SensorReading> getAllReadings() {
        log.info("Fetching all sensor readings...");
        return List.of(toSensorReading(null));
    }

    private SensorReading toSensorReading(final String sensorId) {
        return SensorReading.builder()
                .sensorId(Optional.ofNullable(sensorId).orElse("Sensor-X"))
                .temperature(25.5)
                .humidity(60.0)
                .windSpeed(15.0)
                .timestamp(java.time.LocalDateTime.now())
                .build();
    }
}
