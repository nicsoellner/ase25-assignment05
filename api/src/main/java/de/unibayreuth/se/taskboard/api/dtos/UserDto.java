package de.unibayreuth.se.taskboard.api.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDto {
    @Nullable
    UUID id;
    @Nullable
    LocalDateTime createdAt;
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    String name;



}
