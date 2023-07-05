package QuintoImpacto.testtecnico.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CustomResponse<T> {
    private String message;
    private HttpStatus status;
    private T dto;
}