package com.dekapx.weather.service;

import com.dekapx.weather.common.MetricType;
import com.dekapx.weather.entity.SensorReading;
import com.dekapx.weather.exception.ResourceNotFoundException;
import com.dekapx.weather.repository.SensorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.ToDoubleFunction;

import static com.dekapx.weather.config.CacheConfig.SENSOR_CACHE;

@Slf4j
@Service
@Transactional
public class SensorServiceImpl implements SensorService {
    private static Map<String, MetricType> metricMap = Map.of(
            "TEMPERATURE", MetricType.TEMPERATURE,
            "HUMIDITY", MetricType.HUMIDITY,
            "WIND_SPEED", MetricType.WIND_SPEED);

    private SensorRepository sensorRepository;

    @Autowired
    public SensorServiceImpl(final SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    @Cacheable(value = SENSOR_CACHE, key = "#sensorId")
    public List<SensorReading> getReadings(final String sensorId) {
        log.info("Fetching sensor reading for sensorId: [{}]", sensorId);
        List<SensorReading> sensorReadings = this.sensorRepository.findBySensorId(sensorId);
        validateSensorReadings(sensorId, sensorReadings);
        return sensorReadings;
    }

    @Override
    public List<SensorReading> getAllReadings() {
        log.info("Fetching all sensor readings...");
        List<SensorReading> sensorReadings = new ArrayList<>();
        this.sensorRepository.findAll().forEach(sensorReadings::add);
        return sensorReadings;
    }

    private void validateSensorReadings(String sensorId, List<SensorReading> sensorReadings) {
        Optional.ofNullable(sensorReadings)
                .filter(readings -> !readings.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No readings found for Sensors id [" + sensorId + "]"));
    }

    private MetricType getMetricType(String metric) {
        return Optional.ofNullable(metricMap.get(metric))
                .orElseThrow(() -> new IllegalArgumentException("Invalid metric: " + metric + ". Valid metrics are TEMPERATURE, HUMIDITY, WIND_SPEED"));
    }

    private double calculateAverage(List<SensorReading> readings, MetricType metricType) {
        return readings.stream()
                .mapToDouble(getSensorReadingAsDouble(metricType))
                .average()
                .orElse(Double.NaN);
    }

    private ToDoubleFunction<SensorReading> getSensorReadingAsDouble(MetricType metricType) {
        return reading -> {
            switch (metricType) {
                case TEMPERATURE:
                    return reading.getTemperature();
                case HUMIDITY:
                    return reading.getHumidity();
                case WIND_SPEED:
                    return reading.getWindSpeed();
                default:
                    throw new IllegalArgumentException("Invalid metric: " + metricType);
            }
        };
    }
}
