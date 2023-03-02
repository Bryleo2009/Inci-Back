package com.ofsystem.InciCerti.Controller;

import com.ofsystem.InciCerti.Mapper.UnidadRecepMapper;
import com.ofsystem.InciCerti.Model.UnidadRecep;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/unidadRecep")
public class UnidadRecepController {
    @Autowired
    private UnidadRecepMapper uniMapper;

    @GetMapping("/{UR}")
    public List<UnidadRecep> listarPorId(@PathVariable("UR") String UR) {
        System.out.println("***********");
        return uniMapper.getUR(UR);
    }
}
