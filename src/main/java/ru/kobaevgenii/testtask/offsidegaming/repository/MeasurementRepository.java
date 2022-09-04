package ru.kobaevgenii.testtask.offsidegaming.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kobaevgenii.testtask.offsidegaming.domain.Measurement;
import ru.kobaevgenii.testtask.offsidegaming.domain.User;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    Page<Measurement> findAllByUser(User user, PageRequest pageRequest);
}
