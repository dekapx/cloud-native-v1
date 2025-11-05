package com.dekapx.weather.controller;

import com.dekapx.weather.entity.SensorReading;
import com.dekapx.weather.model.SensorReadingModel;
import com.dekapx.weather.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dekapx.weather.common.ResourceUrls.BASE_URL;
import static com.dekapx.weather.common.ResourceUrls.INFO_URL;
import static com.dekapx.weather.common.ResourceUrls.SENSOR_BY_ID_URL;
import static com.dekapx.weather.common.ResourceUrls.SENSOR_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class SensorController {
    private SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Operation(summary = "Weather API Info")
    @GetMapping(INFO_URL)
    public String getInfo() {
        log.info("Getting Weather API Info");
        return "Weather API v1.0";
    }

    @Operation(summary = "Get Sensor Readings by Sensor ID")
    @GetMapping(SENSOR_BY_ID_URL)
    public ResponseEntity<List<SensorReadingModel>> getReadings(@PathVariable String sensorId) {
        List<SensorReading> sensorReadings  = this.sensorService.getReadings(sensorId);
        List<SensorReadingModel> models = mapToModels(sensorReadings);
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @Operation(summary = "Get All Sensor Readings")
    @GetMapping(SENSOR_URL)
    public ResponseEntity<List<SensorReadingModel>> getAllReadings() {
        List<SensorReading> sensorReadings  = this.sensorService.getAllReadings();
        List<SensorReadingModel> models = mapToModels(sensorReadings);
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    private List<SensorReadingModel> mapToModels(List<SensorReading> entities) {
        return entities.stream()
                .map(this::mapToModel)
                .toList();
    }

    private SensorReadingModel mapToModel(SensorReading entity) {
        return SensorReadingModel.builder()
                .sensorId(entity.getSensorId())
                .temperature(entity.getTemperature())
                .humidity(entity.getHumidity())
                .windSpeed(entity.getWindSpeed())
                .timestamp(entity.getTimestamp())
                .build();
    }

    private SensorReading mapToEntity(SensorReadingModel model) {
        return SensorReading.builder()
                .sensorId(model.getSensorId())
                .temperature(model.getTemperature())
                .humidity(model.getHumidity())
                .windSpeed(model.getWindSpeed())
                .timestamp(model.getTimestamp())
                .build();
    }
}
