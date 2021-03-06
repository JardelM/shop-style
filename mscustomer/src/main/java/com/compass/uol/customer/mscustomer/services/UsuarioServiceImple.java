package com.compass.uol.customer.mscustomer.services;

import com.compass.uol.customer.mscustomer.dto.UsuarioDto;
import com.compass.uol.customer.mscustomer.dto.UsuarioFormDto;
import com.compass.uol.customer.mscustomer.entity.Usuario;
import com.compass.uol.customer.mscustomer.exceptions.ResourceNotFoundException;
import com.compass.uol.customer.mscustomer.exceptions.UserAlreadyExistsException;
import com.compass.uol.customer.mscustomer.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioServiceImple implements UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UsuarioDto getUser(Long id) {

        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        if (usuario.isPresent())
            return modelMapper.map(usuario.get() , UsuarioDto.class);

        throw new ResourceNotFoundException("Id não encontrado");
    }

    @Override
    public UsuarioDto updateUser(Long id, UsuarioFormDto body) {

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(body.getEmail());
        usuarioEncontrado.ifPresent(usuario -> {
            if (!Objects.equals(usuarioEncontrado.get().getId() , id))
                throw new UserAlreadyExistsException(usuario.getEmail());
        });
        body.setPassword(encoder.encode(body.getPassword()));
        Usuario usuarioAAtualizar = modelMapper.map(body , Usuario.class);
        usuarioAAtualizar.setId(id);
        Usuario usuarioAtualizado = usuarioRepository.save(usuarioAAtualizar);
        return modelMapper.map(usuarioAtualizado, UsuarioDto.class);
    }


    @Override
    public UsuarioDto createUser(UsuarioFormDto usuarioFormDto) {

        verificaEmail (usuarioFormDto);
        usuarioFormDto.setPassword(encoder.encode(usuarioFormDto.getPassword()));
        Usuario usuarioACriar = modelMapper.map(usuarioFormDto , Usuario.class);
        Usuario usuarioCriado = this.usuarioRepository.save(usuarioACriar);
        return modelMapper.map(usuarioCriado, UsuarioDto.class);
    }

    void verificaEmail(UsuarioFormDto usuarioFormDto) {

        Optional<Usuario> usuarioEncontrado = this.usuarioRepository.findByEmail(usuarioFormDto.getEmail());
        usuarioEncontrado.ifPresent(user -> {
            throw new UserAlreadyExistsException(user.getEmail());
        });

    }


}
