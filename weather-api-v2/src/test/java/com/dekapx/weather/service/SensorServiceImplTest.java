package com.dekapx.weather.service;

import com.dekapx.weather.entity.SensorReading;
import com.dekapx.weather.exception.ResourceNotFoundException;
import com.dekapx.weather.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.dekapx.weather.common.SensorReadingTestData.HUMIDITY;
import static com.dekapx.weather.common.SensorReadingTestData.SENSOR_ID;
import static com.dekapx.weather.common.SensorReadingTestData.TEMPERATURE;
import static com.dekapx.weather.common.SensorReadingTestData.WIND_SPEED;
import static com.dekapx.weather.common.SensorReadingTestData.buildSensorReading;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SensorServiceImplTest {
    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl sensorService;

    @Test
    void givenSensorId_shouldReturnSensorReading() {
        SensorReading reading = buildSensorReading();
        when(sensorRepository.findBySensorId(SENSOR_ID)).thenReturn(List.of(reading));

        List<SensorReading> sensorReadings = this.sensorService.getReadings(SENSOR_ID);

        assertThat(sensorReadings)
                .isNotNull()
                .hasSize(1)
                .first()
                .satisfies(o -> {
                    assertThat(o.getSensorId()).isEqualTo(SENSOR_ID);
                    assertThat(o.getTemperature()).isEqualTo(TEMPERATURE);
                    assertThat(o.getHumidity()).isEqualTo(HUMIDITY);
                    assertThat(o.getWindSpeed()).isEqualTo(WIND_SPEED);
                });
        verify(sensorRepository, times(1)).findBySensorId(SENSOR_ID);
    }

    @Test
    void givenSensorId_shouldThrowResourceNotFoundException() {
        when(sensorRepository.findBySensorId(SENSOR_ID)).thenReturn(null);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.sensorService.getReadings(SENSOR_ID);
        });

        String expectedMessage = "No readings found for Sensors id [" + SENSOR_ID + "]";
        String actualMessage = exception.getMessage();

        assertThat(expectedMessage).isEqualTo(actualMessage);
        verify(sensorRepository, times(1)).findBySensorId(SENSOR_ID);
    }

    @Test
    void givenNothing_shouldReturnAllSensorReadings() {
        SensorReading reading = buildSensorReading();
        when(sensorRepository.findAll()).thenReturn(List.of(reading));

        List<SensorReading> sensorReadings = this.sensorService.getAllReadings();

        assertThat(sensorReadings)
                .isNotNull()
                .hasSize(1)
                .first()
                .satisfies(o -> {
                    assertThat(o.getSensorId()).isEqualTo(SENSOR_ID);
                    assertThat(o.getTemperature()).isEqualTo(TEMPERATURE);
                    assertThat(o.getHumidity()).isEqualTo(HUMIDITY);
                    assertThat(o.getWindSpeed()).isEqualTo(WIND_SPEED);
                });
        verify(sensorRepository, times(1)).findAll();
    }
}
