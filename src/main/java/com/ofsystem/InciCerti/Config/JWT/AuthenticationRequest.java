package com.ofsystem.InciCerti.Config.JWT;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String dni;
    private String password;

    // getters y setters
}
