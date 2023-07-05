package QuintoImpacto.testtecnico.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static <T> ResponseEntity<CustomResponse<T>> createResponse(T data, String message, HttpStatus status) {
        CustomResponse<T> response = new CustomResponse<>(message, status,data);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<CustomResponse<T>> forbiddenResponse() {
        return createResponse(null, MessageRepository.getNoAccessErrorMessage(), HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<CustomResponse<T>> badRequestResponse(String message) {
        return createResponse(null, message, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<CustomResponse<T>> okResponse(String message) {
        return createResponse(null, message, HttpStatus.OK);
    }

    public static <T> ResponseEntity<CustomResponse<T>> dataResponse(T object, String message) {
        return createResponse(object, (message == null ? MessageRepository.getDataLoadedSuccessfullyMessage() : message), HttpStatus.OK);
    }

    public static <T> ResponseEntity<CustomResponse<T>> createdResponse(T object) {
        return createResponse(object, "Creado correctamente", HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<CustomResponse<T>> updatedResponse(T object) {
        return createResponse(object, "Modificado correctamente", HttpStatus.OK);
    }

    public static <T> ResponseEntity<CustomResponse<T>> deletedResponse() {
        return createResponse(null,"Eliminado correctamente", HttpStatus.OK);
    }

    public static <T> ResponseEntity<CustomResponse<T>> activeDesactiveResponse(Boolean status) {
        return createResponse(null,status == false ? "Se ha desactivado correctamente" : "Se ha vuelto a activar correctamente", HttpStatus.OK);
    }
}
