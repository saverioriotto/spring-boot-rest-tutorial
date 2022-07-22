package com.saverioriotto.springbootresttutorial.controllers;

import com.saverioriotto.springbootresttutorial.entities.Ruolo;
import com.saverioriotto.springbootresttutorial.entities.Utente;
import com.saverioriotto.springbootresttutorial.models.RuoloEnum;
import com.saverioriotto.springbootresttutorial.payload.Credenziali;
import com.saverioriotto.springbootresttutorial.payload.Registrazione;
import com.saverioriotto.springbootresttutorial.repositories.RuoloRepository;
import com.saverioriotto.springbootresttutorial.repositories.UtenteRepository;
import com.saverioriotto.springbootresttutorial.response.JwtResponse;
import com.saverioriotto.springbootresttutorial.response.MessageResponse;
import com.saverioriotto.springbootresttutorial.security.JwtUtils;
import com.saverioriotto.springbootresttutorial.security.services.DettagliUtente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController{

    @Autowired
    private UtenteRepository userRepo;
    @Autowired private JwtUtils jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private RuoloRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Credenziali loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        DettagliUtente userDetails = (DettagliUtente) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/registrazione")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Registrazione signUpRequest) {

        Optional<Utente> username = userRepo.cercaPerUsername(signUpRequest.getUsername());
        if (username.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        Optional<Utente> email = userRepo.cercaPerEmail(signUpRequest.getEmail());
        if (email.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        Utente user = new Utente(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRuoli();

        Set<Ruolo> roles = new HashSet<>();
        if (strRoles == null) {
            Ruolo userRole = roleRepository.cercaPerNome(RuoloEnum.UTENTE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.1"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Ruolo adminRole = roleRepository.cercaPerNome(RuoloEnum.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found.2"));
                        roles.add(adminRole);
                        break;
                    case "MODERATORE":
                        Ruolo modRole = roleRepository.cercaPerNome(RuoloEnum.MODERATORE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found.3"));
                        roles.add(modRole);
                        break;
                    default:
                        Ruolo userRole = roleRepository.cercaPerNome(RuoloEnum.UTENTE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found.4"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepo.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}