package pl.projekt_symulator.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private Long Id;
    private LocalDate start;
    private LocalDate end;
}
  //  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")

 //   @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
