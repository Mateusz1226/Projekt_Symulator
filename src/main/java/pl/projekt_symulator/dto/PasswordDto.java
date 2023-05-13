package pl.projekt_symulator.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {


    @NotEmpty(message = "Wpisz hasło")
    @Size(min = 6, max = 30)
    private String oldPassword;

    @NotEmpty(message = "Wpisz hasło")
    @Size(min = 6, max = 30)
    private String newPassword1;

    @NotEmpty(message = "Wpisz hasło")
    @Size(min = 6, max = 30)
    private String newPassword2;


}
