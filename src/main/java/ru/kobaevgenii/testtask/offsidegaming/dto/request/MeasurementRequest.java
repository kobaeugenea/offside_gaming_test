package ru.kobaevgenii.testtask.offsidegaming.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MeasurementRequest {
    @NotEmpty
    public String username;
    @NotNull
    @Min(value = 0, message = "Must be greater or equal to 0")
    public int coldWaterUsedLiter;
    @NotNull
    @Min(value = 0, message = "Must be greater or equal to 0")
    public int hotWaterUsedLiter;
    @NotNull
    @Min(value = 0, message = "Must be greater or equal to 0")
    public int gasUsedCubicMeter;
}
