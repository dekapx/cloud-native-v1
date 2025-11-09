package com.dekapx.weather.service;

import com.dekapx.weather.entity.SensorReading;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorService {
    List<SensorReading> getReadings(String sensorId);

    List<SensorReading> getAllReadings();
}
