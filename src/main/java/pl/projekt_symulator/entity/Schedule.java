package pl.projekt_symulator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booked_appointments")

public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    private LocalDateTime end;


    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull
    private LocalDateTime start;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Schedule(LocalDateTime start, LocalDateTime end, User mapToEntity) {

    }


}