package QuintoImpacto.testtecnico;

import QuintoImpacto.testtecnico.models.*;
import QuintoImpacto.testtecnico.repositories.AdministradorRepository;
import QuintoImpacto.testtecnico.repositories.AlumnoRepository;
import QuintoImpacto.testtecnico.repositories.CursoRepository;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class TestTecnicoQuintoImpactoApplication {

	@Autowired
	PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(TestTecnicoQuintoImpactoApplication.class, args);
	}

	//Dejo el codigo comentado de las primeros objetos que se agregaron a la bd
	@Bean
	public CommandLineRunner initData(AlumnoRepository alumnoRepository, ProfesorRepository profesorRepository, AdministradorRepository administradorRepository, CursoRepository cursoRepository){

		return (args) -> {
//			Alumno alumno1 = new Alumno();
//			alumno1.setApellido("Perez");
//			alumno1.setNombre("Juan");
//			alumno1.setDni("33111333");
//			alumno1.setEmail("alumno1@gmail.com");
//			alumno1.setCelular("3547654824");
//			alumno1.setPassword(passwordEncoder.encode("123"));
//			alumno1.setDeleted(false);
//
//			Alumno alumno2 = new Alumno();
//			alumno2.setApellido("Perezz");
//			alumno2.setNombre("Juanss");
//			alumno2.setDni("2222");
//			alumno2.setEmail("alumno2@gmail.com");
//			alumno2.setCelular("12312354824");
//			alumno2.setPassword(passwordEncoder.encode("123"));
//			alumno2.setDeleted(false);
//
//			Alumno alumno3 = new Alumno();
//			alumno3.setApellido("Juancito");
//			alumno3.setNombre("Cuscus");
//			alumno3.setDni("222222");
//			alumno3.setEmail("alumno3@gmail.com");
//			alumno3.setCelular("12354824");
//			alumno3.setPassword(passwordEncoder.encode("123"));
//			alumno3.setDeleted(false);
//
//			alumnoRepository.save(alumno1);
//			alumnoRepository.save(alumno2);
//			alumnoRepository.save(alumno3);
//
//			Profesor profesor1 = new Profesor();
//			profesor1.setApellido("Profesor");
//			profesor1.setNombre("Jirafales");
//			profesor1.setDni("123abc");
//			profesor1.setEmail("profe1@gmail.com");
//			profesor1.setCelular("12333");
//			profesor1.setPassword(passwordEncoder.encode("123"));
//			profesor1.setDeleted(false);
//
//			Profesor profesor2= new Profesor();
//			profesor2.setApellido("Profe");
//			profesor2.setNombre("Sor");
//			profesor2.setDni("2a33");
//			profesor2.setEmail("profe2@gmail.com");
//			profesor2.setCelular("35824");
//			profesor2.setPassword(passwordEncoder.encode("123"));
//			profesor2.setDeleted(false);
//
//			profesorRepository.save(profesor1);
//			profesorRepository.save(profesor2);
//
//			Administrador administrador1 = new Administrador();
//			administrador1.setApellido("Admin");
//			administrador1.setNombre("Pepo");
//			administrador1.setDni("ahc23");
//			administrador1.setEmail("admin1@gmail.com");
//			administrador1.setCelular("113232321");
//			administrador1.setPassword(passwordEncoder.encode("123"));
//			administrador1.setDeleted(false);
//
//			administradorRepository.save(administrador1);
//
//			List<Alumno> alumnoList = new ArrayList<>();
//			alumnoList.add(alumno1);
//			alumnoList.add(alumno2);
//
//			List<Alumno> alumnoList2 = new ArrayList<>();
//			alumnoList2.add(alumno2);
//			alumnoList2.add(alumno3);
//
//			Curso curso1 = new Curso();
//			curso1.setNombre("Biologia I");
//			curso1.setDeleted(false);
//			curso1.setProfesor(profesor1);
//			curso1.setAlumnos(alumnoList);
//			curso1.setTurno(Turno.T);
//
//			Curso curso2 = new Curso();
//			curso2.setNombre("Fisica Cuantica I");
//			curso2.setDeleted(false);
//			curso2.setProfesor(profesor2);
//			curso2.setAlumnos(alumnoList2);
//			curso2.setTurno(Turno.M);
//
//			Curso curso3 = new Curso();
//			curso3.setNombre("Pasteleria I");
//			curso3.setDeleted(false);
//			curso3.setProfesor(profesor2);
//			curso3.setAlumnos(alumnoList2);
//			curso3.setTurno(Turno.N);
//
//			cursoRepository.save(curso1);
//			cursoRepository.save(curso2);
//			cursoRepository.save(curso3);
		};
	}

}
