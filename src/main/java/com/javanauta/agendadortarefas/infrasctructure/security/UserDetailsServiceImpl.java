package com.javanauta.agendadortarefas.infrasctructure.security;


import com.javanauta.agendadortarefas.business.dto.request.LoginRequest;
import com.javanauta.agendadortarefas.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UsuarioClient client;

    public UserDetails carregaDadosUsuario(String email, String token) {
        LoginRequest request = client.buscarUsuarioPorEmail(email, token);

        return User
                .withUsername(request.getEmail())
                .password(request.getSenha())
                .build();
    }

}
