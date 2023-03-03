package com.ofsystem.InciCerti.Mapper;

import com.ofsystem.InciCerti.Model.UnidadRecep;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UnidadRecepMapper {
    @Select("select UR.unidad_recepcion, UR.nro_alterno, UR.nro_folio,\n" +
            "TDT.nombre , LT.nro_caja  from public.tb_unit_reception UR\n" +
            "inner join public.tb_lot LT\n" +
            "on LT.id = UR.id_lote\n" +
            "inner join public.tb_documentary_type DT\n" +
            "on DT.id = UR.id_documentary_type\n" +
            "inner join public.tb_template_documentary_type TDT\n" +
            "on TDT.id = DT.id_template_documentary_type\n" +
            "where UR.unidad_recepcion = #{UR};")
    UnidadRecep getUR(String UR);

    @Select("select distinct (unidad_recepcion) from public.tb_unit_reception;")
    List<String> getDistinctUR();

    @Select("select LC.process from public.tb_lot_customer_satate_user LC\n" +
            "inner join public.tb_unit_reception UR\n" +
            "on UR.id = LC.id_unit_reception\n" +
            "where UR.unidad_recepcion = #{UR}\n" +
            "and estado = 'DISPONIBLE' and procesado is false;")
    String getProceso(String UR);
}
