package cz.cvut.fel.nss.gradeningrental.userservice.DTO;

import cz.cvut.fel.nss.gradeningrental.userservice.model.Role;
import cz.cvut.fel.nss.gradeningrental.userservice.model.User;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private Set<Role> roles;
    private String fullName;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.fullName = user.getFullName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
