package ru.kobaevgenii.testtask.offsidegaming.dto.response;

import lombok.Data;

@Data
public class MeasurementResponse {
    public long id;
    public String username;
    public int coldWaterUsedLiter;
    public int hotWaterUsedLiter;
    public int gasUsedCubicMeter;
}
