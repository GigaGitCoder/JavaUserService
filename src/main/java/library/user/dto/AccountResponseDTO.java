package library.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import library.user.entity.Role;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO implements Serializable {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private Role role;
}
