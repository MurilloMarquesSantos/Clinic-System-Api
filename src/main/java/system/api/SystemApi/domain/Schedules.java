package system.api.SystemApi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.api.SystemApi.domain.enums.AvailabilityStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_schedule")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctors doctor;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus available;
}
