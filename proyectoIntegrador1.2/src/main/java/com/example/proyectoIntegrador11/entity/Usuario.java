package com.example.proyectoIntegrador11.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String userName;
    @Column
    private String email;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private UsuarioRole usuarioRole;

    public Usuario() {
    }

    public Usuario(String nombre, UsuarioRole usuarioRole, String password, String email, String userName) {
        this.nombre = nombre;
        this.usuarioRole = usuarioRole;
        this.password = password;
        this.email = email;
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //este metodo permite saber cuales son las autirizaciones que tiene el usuario en cuestion
        SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority(usuarioRole.name());

        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}