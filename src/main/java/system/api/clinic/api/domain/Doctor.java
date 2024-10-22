package system.api.clinic.api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_doctors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Doctor name is mandatory")
    private String name;

    @NotEmpty(message = "Doctor specialty is mandatory")
    private String specialty;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Doctor email is mandatory")
    private String email;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedule;
}
