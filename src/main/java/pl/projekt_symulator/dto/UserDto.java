package pl.projekt_symulator.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty(message = "Wpisz imię")
    private String firstName;
    @NotEmpty(message = "Wpisz nazwisko")
    private String lastName;

    @Email
    @NotEmpty(message = "Wpisz adres email")
    private String email;

    @NotEmpty(message = "Wpisz hasło")
    @Size(min = 6, max = 30)
    private String password;


    private Boolean marketingAgreement;
   // @Length(min = 9,max = 12, message = "Proszę wpisać poprawny numer telefonu")
    @Pattern(regexp = "\\d{9}",message = "Wpisz poprawny numer telefonu")
    private String phoneNumber;

    @Range(min = 8,max = 110, message = "Wpisz poprawny wiek")
    private int age;

    public UserDto(String email) {
        this.email = email;
    }

    public UserDto(Long id,String email) {
        this.email = email;
    }
}