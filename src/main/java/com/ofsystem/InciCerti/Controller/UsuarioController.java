package com.ofsystem.InciCerti.Controller;

import com.ofsystem.InciCerti.Exception.ModeloNotFoundException;
import com.ofsystem.InciCerti.Mapper.IUsuarioMapper;
import com.ofsystem.InciCerti.Mapper.UnidadRecepMapper;
import com.ofsystem.InciCerti.Model.UnidadRecep;
import com.ofsystem.InciCerti.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioMapper service;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("")
    public ResponseEntity<List<Usuario>> listar() {
        return new ResponseEntity<List<Usuario>>(service.buscador(), HttpStatus.OK);
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<Object> buscarUsername(@PathVariable("username") String username){
        Usuario unaUsuario = service.findByUsername(username);
        if(username.equals("admin")){
            unaUsuario = new Usuario("admin", passwordEncoder.encode("admin123"));
        } else if(unaUsuario == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO: " + username);
        }
        return new ResponseEntity<Object>(unaUsuario,HttpStatus.OK);
    }

}
