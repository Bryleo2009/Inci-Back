package com.ofsystem.InciCerti.Controller;

import com.ofsystem.InciCerti.Exception.ModeloNotFoundException;
import com.ofsystem.InciCerti.Mapper.UnidadRecepMapper;
import com.ofsystem.InciCerti.Model.UnidadRecep;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unidadRecep")
public class UnidadRecepController {
    @Autowired
    private UnidadRecepMapper service;

    @GetMapping("/{UR}")
    public ResponseEntity<UnidadRecep> listarPorId(@PathVariable("UR") String UR) {
        UnidadRecep unaUnidad = service.getUR(UR);
        if(unaUnidad == null){
            throw new ModeloNotFoundException("UNIDAD NO ENCONTRADA: " + UR);
        } else return new ResponseEntity<UnidadRecep>(unaUnidad, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<String>> listar() {
        return new ResponseEntity<List<String>>(service.getDistinctUR(), HttpStatus.OK);
    }

    @GetMapping("/proceso/{UR}")
    public ResponseEntity<String> proceso(@PathVariable("UR") String UR) {
        return new ResponseEntity<String>(service.getProceso(UR), HttpStatus.OK);
    }
}
