package cz.cvut.fel.ear.pujcovna.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryCreationDTO {
    @NotBlank(message = "Category needs to have a nonblank name")
    private String name;

    public CategoryCreationDTO() {
    }

    public CategoryCreationDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
