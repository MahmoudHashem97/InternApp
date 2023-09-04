package com.intern.app.qeema.service.securityService;

import com.intern.app.qeema.model.DtoModels.InternRequest;
import com.intern.app.qeema.model.DtoModels.Role;
import com.intern.app.qeema.model.entities.Intern;
import com.intern.app.qeema.model.secureDto.AuthenticationRequest;
import com.intern.app.qeema.model.secureDto.AuthenticationResponse;
import com.intern.app.qeema.reprosatory.InternRepo;
import com.intern.app.qeema.service.securityService.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationSevice {
    private final InternRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(InternRequest request) {
        var user = Intern.builder()
                .email(request.getEmail())
                .gpa(request.getGpa())
                .desiredTrack(request.getDesiredTrack())
                .university(request.getUniversity())
                .cvUrl(request.getCvUrl())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.User)
                .build();
        repository.save(user);
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var user =repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();    }
}
