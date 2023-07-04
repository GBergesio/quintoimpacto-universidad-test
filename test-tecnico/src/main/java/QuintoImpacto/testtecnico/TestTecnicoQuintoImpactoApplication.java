package QuintoImpacto.testtecnico;

import QuintoImpacto.testtecnico.models.Alumno;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestTecnicoQuintoImpactoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestTecnicoQuintoImpactoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(AlumnoRepository alumnoRepository){
		return (args) -> {
			Alumno alumno1 = new Alumno();
			alumno1.setApellido("Perez");
			alumno1.setNombre("Juan");
			alumno1.setDni("33111333");
			alumno1.setEmail("perez_juan@gmail.com");
			alumno1.setCelular("3547654824");
			alumno1.setPassword("123");
			alumno1.setDeleted(false);

			Alumno alumno2 = new Alumno();
			alumno2.setApellido("Perezz");
			alumno2.setNombre("Juanss");
			alumno2.setDni("2222");
			alumno2.setEmail("perezss_juan@gmail.com");
			alumno2.setCelular("12312354824");
			alumno2.setPassword("122223");
			alumno2.setDeleted(false);

			alumnoRepository.save(alumno1);
			alumnoRepository.save(alumno2);
		};
	}

}
