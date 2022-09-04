package ru.kobaevgenii.testtask.offsidegaming.domain;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Measurement {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private int coldWaterUsedLiter;
    private int hotWaterUsedLiter;
    private int gasUsedCubicMeter;
}
