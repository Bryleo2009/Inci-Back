package com.ofsystem.InciCerti.Mapper;

import com.ofsystem.InciCerti.Model.Usuario;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IUsuarioMapper {
    @Select("select dni, password from public.tb_user;")
    List<Usuario> buscador();

    @Select("select username as dni, password from public.tb_user\n" +
            "where username = #{UR};")
    Usuario findByUsername (String UR);
}
