package com.ofsystem.InciCerti.Controller;

import com.ofsystem.InciCerti.Exception.ModeloNotFoundException;
import com.ofsystem.InciCerti.Mapper.UnidadRecepMapper;
import com.ofsystem.InciCerti.Model.UnidadRecep;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity cambioProceso(@RequestParam("UR") int UR,
                                        @RequestParam("newProcess") String newProcess){
        if(newProcess.equals("PROCESS_DIGITALIZACION")){
            System.out.println("Voy a Digitalizacion");
            service.deleteOne(UR);
            service.deleteTwo(UR);
            service.deleteThree(UR);
            service.deleteFour(UR);
        }
        service.changeProcess(UR);
        service.registrarNewProcess(UR,newProcess);
        return new ResponseEntity(HttpStatus.OK);
    }
}
