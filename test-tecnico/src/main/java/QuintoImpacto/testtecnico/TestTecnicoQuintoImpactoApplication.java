package QuintoImpacto.testtecnico;

import QuintoImpacto.testtecnico.models.Alumno;
import QuintoImpacto.testtecnico.models.Profesor;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
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
	public CommandLineRunner initData(AlumnoRepository alumnoRepository, ProfesorRepository profesorRepository){
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

			Profesor profesor1 = new Profesor();
			profesor1.setApellido("Profesor");
			profesor1.setNombre("Jirafales");
			profesor1.setDni("123abc");
			profesor1.setEmail("profe_jirafales@gmail.com");
			profesor1.setCelular("12333");
			profesor1.setPassword("12345");
			profesor1.setDeleted(false);

			Profesor profesor2= new Profesor();
			profesor2.setApellido("Profe");
			profesor2.setNombre("Sor");
			profesor2.setDni("2a33");
			profesor2.setEmail("profe_sor@gmail.com");
			profesor2.setCelular("35824");
			profesor2.setPassword("12323");
			profesor2.setDeleted(false);

			profesorRepository.save(profesor1);
			profesorRepository.save(profesor2);
		};
	}

}
