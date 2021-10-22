package pl.kwidzinski.curencyexchanger.exceptions.definition;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(Class<?> clazz, Object... params) {
        super("Entity: " + clazz.getSimpleName() + " not found using parameters: " + Arrays.stream(params)
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }
}
