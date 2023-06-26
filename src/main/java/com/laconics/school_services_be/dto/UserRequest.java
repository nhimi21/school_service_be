package com.laconics.school_services_be.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "fullName is required")
    private String fullName;

    @NotNull(message = "username is required")
    private String username;

    @NotNull(message = "password is required")
    private String password;

}
