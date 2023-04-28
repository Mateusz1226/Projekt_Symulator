package pl.projekt_symulator.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty(message = "Imię nie może być puste")
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email nie może być pusty")
    @Email
    private String email;
    @NotEmpty(message = "Hasło nie może być puste")
    private String password;


    private Boolean marketingAgreement;
    private String phoneNumber;
    private int age;
}