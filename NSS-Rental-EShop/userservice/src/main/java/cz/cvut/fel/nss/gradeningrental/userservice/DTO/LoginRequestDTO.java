package cz.cvut.fel.nss.gradeningrental.userservice.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    public LoginRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username cannot be blank") String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be blank") String password) {
        this.password = password;
    }
}
