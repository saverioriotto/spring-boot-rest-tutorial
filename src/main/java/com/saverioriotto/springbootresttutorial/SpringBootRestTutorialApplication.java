package com.saverioriotto.springbootresttutorial;

import com.saverioriotto.springbootresttutorial.entities.Libro;
import com.saverioriotto.springbootresttutorial.entities.Ruolo;
import com.saverioriotto.springbootresttutorial.entities.Utente;
import com.saverioriotto.springbootresttutorial.models.RuoloEnum;
import com.saverioriotto.springbootresttutorial.repositories.LibroRepository;
import com.saverioriotto.springbootresttutorial.repositories.RuoloRepository;
import com.saverioriotto.springbootresttutorial.repositories.UtenteRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class SpringBootRestTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestTutorialApplication.class, args);
	}

	@Autowired
	private UtenteRepository userRepository;
	@Autowired
	private RuoloRepository ruoloRepository;
	@Autowired
	private LibroRepository libroRepository;

	@Bean
	InitializingBean initDatabaseSampleData() {
		return () -> {

			Optional<Utente> u = userRepository.cercaPerUsername("test");

			if(!u.isPresent()){
				ruoloRepository.save(new Ruolo(1, RuoloEnum.ADMIN));
				ruoloRepository.save(new Ruolo(2, RuoloEnum.MODERATORE));
				ruoloRepository.save(new Ruolo(3, RuoloEnum.UTENTE));
				Set<Ruolo> ruoli = new HashSet<>();
				ruoli.add(new Ruolo(1, RuoloEnum.UTENTE));
				ruoli.add(new Ruolo(2, RuoloEnum.ADMIN));
				ruoli.add(new Ruolo(3, RuoloEnum.MODERATORE));

				userRepository.save(new Utente(
						"test",
						"test@saverioriotto.it",
						"$2a$10$hTfPvJ9uPH7CX/W5TtcuWe99xCaAm/U/6tvY/8t4/NYjv212C7nMS",
						ruoli));

				libroRepository.save(new Libro("Clean Code: A Handbook of Agile Software Craftsmanship","Robert C. Martin" ));
				libroRepository.save(new Libro("Introduction to Algorithms","Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein" ));
				libroRepository.save(new Libro("Design Patterns: Elements of Reusable Object-Oriented Software","Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides, Grady Booch (Foreword)" ));
			}
		};
	}

}
