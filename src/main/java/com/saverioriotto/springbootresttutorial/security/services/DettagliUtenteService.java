package com.saverioriotto.springbootresttutorial.security.services;

import com.saverioriotto.springbootresttutorial.entities.Utente;
import com.saverioriotto.springbootresttutorial.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DettagliUtenteService implements UserDetailsService {
    @Autowired
    UtenteRepository utenteRepository;
    @Override
    @Transactional
    public DettagliUtente loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente user = utenteRepository.cercaPerUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return DettagliUtente.build(user);
    }
}