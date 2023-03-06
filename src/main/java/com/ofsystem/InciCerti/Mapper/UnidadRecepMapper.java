package com.ofsystem.InciCerti.Mapper;

import com.ofsystem.InciCerti.Model.Proceso;
import com.ofsystem.InciCerti.Model.UnidadRecep;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UnidadRecepMapper {

    @Select("select id from tb_unit_reception where unidad_recepcion = #{UR}")
    int buscador(String UR);

    @Select("select UR.id ,UR.unidad_recepcion, UR.nro_alterno, UR.nro_folio,\n" +
            "DT.id as \"DT\" , LT.nro_caja  from public.tb_unit_reception UR\n" +
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

    @Delete("Delete from tb_metadata_image\n" +
            "where id_image in (\n" +
            "\tselect id from tb_image ti where id_document in (\n" +
            "\t\tselect id from tb_document td where id_unit_reception = #{UR}\n" +
            "\t)\n" +
            ");")
    void deleteOne(int UR);

    @Delete("delete from tb_lot_incidence\n" +
            "where id_imagen in (\n" +
            "\tselect id from tb_image ti where id_document in (\n" +
            "\t\tselect id from tb_document td where id_unit_reception = #{UR}\n" +
            "\t)\n" +
            ");\n")
    void deleteTwo(int UR);

    @Delete("delete from tb_image ti \n" +
            "where id_document in (\n" +
            "\tselect id from tb_document td \n" +
            "\twhere id_unit_reception = #{UR}\n" +
            ");\n")
    void deleteThree(int UR);

    @Delete("DELETE from tb_document td where id_unit_reception = #{UR};")
    void deleteFour(int UR);

    @Update("UPDATE tb_lot_customer_satate_user\n" +
            "SET estado = 'ASIGNADO', fecha_asignacion_proceso = (\n" +
            "\tselect LC.fecha_fin_proceso from public.tb_lot_customer_satate_user LC\n" +
            "\tinner join public.tb_unit_reception UR\n" +
            "\ton UR.id = LC.id_unit_reception\n" +
            "\twhere UR.id = #{UR} and LC.fecha_fin_proceso is not null\n" +
            "\tORDER BY LC.fecha_asignacion_proceso desc\n" +
            "\tLIMIT 1\n" +
            "), procesado =  true, id_usuario = (\n" +
            "\tselect LC.id_usuario from public.tb_lot_customer_satate_user LC\n" +
            "\tinner join public.tb_unit_reception UR\n" +
            "\ton UR.id = LC.id_unit_reception\n" +
            "\twhere UR.id = #{UR}\n" +
            "\tORDER BY LC.fecha_asignacion_proceso desc\n" +
            "\tLIMIT 1 OFFSET 1\n" +
            ")WHERE id_unit_reception = #{UR} and estado = 'DISPONIBLE' and procesado is false;")
    void changeProcess(int UR);
    
    @Insert("INSERT INTO public.tb_lot_customer_satate_user(\n" +
            "estado, flag_solicitud, \n" +
            "procesado, process, process_origen, id_unit_reception)\n" +
            "VALUES ('DISPONIBLE',FALSE,FALSE,#{newProcess},(select LC.process from public.tb_lot_customer_satate_user LC\n" +
            "inner join public.tb_unit_reception UR\n" +
            "on UR.id = LC.id_unit_reception\n" +
            "where UR.id = #{UR}\n" +
            "ORDER BY LC.fecha_asignacion_proceso desc\n" +
            "LIMIT 1), (select id from public.tb_unit_reception\n" +
            "where id = #{UR}));")
    void registrarNewProcess(int UR,String newProcess);


    @Select("select DT.id, TDT.nombre from public.tb_documentary_type DT\n" +
            "inner join public.tb_template_documentary_type TDT\n" +
            "on TDT.id = DT.id_template_documentary_type;")
    List<Proceso> listarProcesos();

    @Select("select id from public.tb_lot LT\n" +
            "where nro_caja = #{UR};")
    String lote(String UR);

    @Update("UPDATE public.tb_unit_reception\n" +
            "SET unidad_recepcion =  #{UR}, nro_alterno =  #{NA},  nro_folio =  #{NF},\n" +
            "id_documentary_type =  #{DT}, id_lote =  #{LT}\n" +
            "WHERE id =  #{id};")
    void changeUR (int id, String UR,String NA, int NF, int DT, int LT);
}
