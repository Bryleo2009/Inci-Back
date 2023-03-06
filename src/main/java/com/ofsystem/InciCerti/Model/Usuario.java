package com.ofsystem.InciCerti.Model;

import lombok.Data;

@Data
public class Usuario {
    public String dni;
    public String password;

    public Usuario(String dni, String password) {
        this.dni = dni;
        this.password = password;
    }
}
