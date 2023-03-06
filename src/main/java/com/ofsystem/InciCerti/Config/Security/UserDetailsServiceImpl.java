package com.ofsystem.InciCerti.Config.Security;

import com.ofsystem.InciCerti.Mapper.IUsuarioMapper;
import com.ofsystem.InciCerti.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IUsuarioMapper service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = service.findByUsername(username);
        if ("admin".equals(username)) {
            // Este es el usuario por defecto
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User("admin", passwordEncoder.encode("admin123"), authorities);
        }


        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + username);
        }
        Set<GrantedAuthority> roles = new HashSet<>();
        /*Rol rol = usuario.getIdRol();*/
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new org.springframework.security.core.userdetails.User(usuario.getDni(), usuario.getPassword(),
                roles);
    }

}
