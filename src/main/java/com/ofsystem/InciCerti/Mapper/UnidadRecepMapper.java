package com.ofsystem.InciCerti.Mapper;

import com.ofsystem.InciCerti.Model.UnidadRecep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UnidadRecepMapper {
    @Select("select UR.unidad_recepcion, UR.nro_alterno, UR.nro_folio,\n" +
            "DF.tipo , LT.nro_caja  from public.tb_unit_reception UR\n" +
            "inner join public.tb_lot LT\n" +
            "on LT.id = UR.id_lote\n" +
            "inner join public.tb_documentary_type DT\n" +
            "on DT.id = UR.id_documentary_type\n" +
            "inner join public.tb_document_format DF\n" +
            "on DF.id = DT.id_document_format\n" +
            "where UR.unidad_recepcion = #{UR};")
    List<UnidadRecep> getUR(String UR);
}
