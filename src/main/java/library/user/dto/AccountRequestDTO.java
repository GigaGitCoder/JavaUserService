package library.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO implements Serializable {
    @NotBlank(message = "Nickname cannot be empty")
    @Size(max = 50, message = "Nickname cannot be longer than 50 characters")
    private String nickname;

    @NotBlank(message = "The email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "The password cannot be empty")
    private String password;
}
