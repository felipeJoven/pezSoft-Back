package com.peces.pezSoft.security;

import com.peces.pezSoft.model.Rol;
import com.peces.pezSoft.model.Usuario;
import com.peces.pezSoft.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService {

    private UsuarioRepository usuarioRepo;

    //Método para traernos una lista de autoridades por medio de una lista de roles
    public Collection<GrantedAuthority> mapToAuthorities(Rol rol){
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Mapear los roles a autoridades
        GrantedAuthority roleAuthorities = new SimpleGrantedAuthority(rol.getName());
        // Agregar las autoridades de los roles a la lista final
        authorities.add(roleAuthorities);
        return authorities;
    }

    //Método para traernos un usuario con todos sus datos por medio de sus username
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuario.getEmail(), usuario.getPassword(), mapToAuthorities(usuario.getRol()));
    }
}