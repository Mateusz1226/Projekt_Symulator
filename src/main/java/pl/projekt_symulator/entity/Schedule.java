package pl.projekt_symulator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@EqualsAndHashCode
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booked_schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate start;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate end;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(LocalDate start, LocalDate end, User mapToEntity) {

    }


}