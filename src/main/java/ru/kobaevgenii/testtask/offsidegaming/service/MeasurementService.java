package ru.kobaevgenii.testtask.offsidegaming.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kobaevgenii.testtask.offsidegaming.domain.Measurement;
import ru.kobaevgenii.testtask.offsidegaming.domain.User;
import ru.kobaevgenii.testtask.offsidegaming.repository.MeasurementRepository;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Transactional
    public Measurement submitMeasurement(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    @Transactional(readOnly = true)
    public Page<Measurement> getSubmittedMeasurementsHistory(User user, int pageNum, int pageSize) {
        return measurementRepository.findAllByUser(user, PageRequest.of(pageNum, pageSize, Sort.by("id")));
    }
}
