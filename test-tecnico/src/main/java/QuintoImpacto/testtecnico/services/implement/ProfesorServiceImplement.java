package QuintoImpacto.testtecnico.services.implement;

import QuintoImpacto.testtecnico.dtos.AlumnoDTO;
import QuintoImpacto.testtecnico.utils.MapperUtil;
import QuintoImpacto.testtecnico.dtos.ProfesorDTO;
import QuintoImpacto.testtecnico.dtos.request.UserRequest;
import QuintoImpacto.testtecnico.models.Profesor;
import QuintoImpacto.testtecnico.repositories.ProfesorRepository;
import QuintoImpacto.testtecnico.services.ProfesorService;
import QuintoImpacto.testtecnico.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServiceImplement implements ProfesorService {

    @Autowired
    ProfesorRepository profesorRepository;

    @Override
    public ResponseEntity<?> getAllProfesores() {
        List<Profesor> profesores = profesorRepository.findAll(); // Obtén la lista de profesores desde alguna fuente de datos
        List<ProfesorDTO> profesorDTOS = MapperUtil.convertToDtoList(profesores, ProfesorDTO.class);
        return ResponseUtils.dataResponse(profesorDTOS, null);
    }

    @Override
    public ResponseEntity<?> createProfesor(UserRequest profesorRequest) {
        List<Profesor> profesores;

        profesores = profesorRepository.findAll();

        Boolean profesorExist = profesores.stream().anyMatch(p -> p.getDni().equals(profesorRequest.getDni()));

        if (StringUtils.isBlank(profesorRequest.getDni()))
            return ResponseUtils.badRequestResponse("Dni requerido");
        if (profesorExist)
            return ResponseUtils.badRequestResponse("Dni en uso");
        if (StringUtils.isBlank(profesorRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre requerido");
        if (StringUtils.isBlank(profesorRequest.getApellido()))
            return ResponseUtils.badRequestResponse("Apellido requerido");
        if (StringUtils.isBlank(profesorRequest.getCelular()))
            return ResponseUtils.badRequestResponse("Celular requerido");
        if (StringUtils.isBlank(profesorRequest.getEmail()))
            return ResponseUtils.badRequestResponse("Email requerido");

        Profesor newProfesor = new Profesor();
        newProfesor.setDni(profesorRequest.getDni());
        newProfesor.setNombre(profesorRequest.getNombre());
        newProfesor.setApellido(profesorRequest.getApellido());
        newProfesor.setCelular(profesorRequest.getCelular());
        newProfesor.setEmail(profesorRequest.getEmail());
        newProfesor.setPassword(profesorRequest.getPassword()); //cambiar con el password encoder
        newProfesor.setDeleted(false);
        profesorRepository.save(newProfesor);

        ProfesorDTO profesorDTO = MapperUtil.convertToDto(newProfesor, ProfesorDTO.class);
        return ResponseUtils.createdResponse(profesorDTO);
    }

    @Override
    public ResponseEntity<?> updateProfesor(Long id, UserRequest profesorRequest) {

        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) {
            return ResponseUtils.badRequestResponse("Profesor no encontrado");
        }
        if(!profesor.getDni().equals(profesorRequest.getDni())){
            Profesor profesorDni = profesorRepository.findByDni(profesorRequest.getDni());
            if(profesorDni != null){
                return ResponseUtils.badRequestResponse("DNI en uso");
            }
        }
        if (StringUtils.isBlank(profesorRequest.getDni()))
            return ResponseUtils.badRequestResponse("Dni requerido");
        if (StringUtils.isBlank(profesorRequest.getNombre()))
            return ResponseUtils.badRequestResponse("Nombre requerido");
        if (StringUtils.isBlank(profesorRequest.getApellido()))
            return ResponseUtils.badRequestResponse("Apellido requerido");
        if (StringUtils.isBlank(profesorRequest.getCelular()))
            return ResponseUtils.badRequestResponse("Celular requerido");
        if (StringUtils.isBlank(profesorRequest.getEmail()))
            return ResponseUtils.badRequestResponse("Email requerido");

        profesor.setDni(profesorRequest.getDni());
        profesor.setNombre(profesorRequest.getNombre());
        profesor.setApellido(profesorRequest.getApellido());
        profesor.setCelular(profesorRequest.getCelular());
        profesor.setEmail(profesorRequest.getEmail());
        profesor.setPassword(profesorRequest.getPassword()); //cambiar con el password encoder
        profesor.setDeleted(false);
        profesorRepository.save(profesor);
        ProfesorDTO profesorDTO = MapperUtil.convertToDto(profesor, ProfesorDTO.class);
        return ResponseUtils.updatedResponse(profesorDTO);
    }

    @Override
    public ResponseEntity<?> deleteProfesor(Long id) {
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) {
            return ResponseUtils.badRequestResponse("Profesor no encontrado");
        }
        profesor.setDeleted(!profesor.getDeleted());
        profesorRepository.save(profesor);

        return ResponseUtils.activeDesactiveResponse(!profesor.getDeleted());
    }
}
