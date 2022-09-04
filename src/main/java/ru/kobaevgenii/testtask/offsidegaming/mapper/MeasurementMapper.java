package ru.kobaevgenii.testtask.offsidegaming.mapper;

import org.springframework.stereotype.Component;
import ru.kobaevgenii.testtask.offsidegaming.domain.Measurement;
import ru.kobaevgenii.testtask.offsidegaming.domain.User;
import ru.kobaevgenii.testtask.offsidegaming.dto.request.MeasurementRequest;
import ru.kobaevgenii.testtask.offsidegaming.dto.response.MeasurementResponse;

@Component
public class MeasurementMapper {
    public Measurement fromDTO(MeasurementRequest measurementRequest, User user) {
        Measurement measurement = new Measurement();
        measurement.setUser(user);
        measurement.setColdWaterUsedLiter(measurementRequest.getColdWaterUsedLiter());
        measurement.setHotWaterUsedLiter(measurementRequest.getHotWaterUsedLiter());
        measurement.setGasUsedCubicMeter(measurementRequest.getGasUsedCubicMeter());
        return measurement;
    }

    public MeasurementResponse toDTO(Measurement measurement) {
        MeasurementResponse measurementResponse = new MeasurementResponse();
        measurementResponse.setId(measurement.getId());
        measurementResponse.setUsername(measurement.getUser().getName());
        measurementResponse.setColdWaterUsedLiter(measurement.getColdWaterUsedLiter());
        measurementResponse.setHotWaterUsedLiter(measurement.getHotWaterUsedLiter());
        measurementResponse.setGasUsedCubicMeter(measurement.getGasUsedCubicMeter());
        return measurementResponse;
    }
}
