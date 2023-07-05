package QuintoImpacto.testtecnico.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {
    private static ModelMapper modelMapper = new ModelMapper();

    public static <T, D> D convertToDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public static <T, D> List<D> convertToDtoList(List<T> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> convertToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }
}
