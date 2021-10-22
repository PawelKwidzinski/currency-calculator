package pl.kwidzinski.curencyexchanger.exceptions.definition;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ObjectFoundException extends RuntimeException {

    public ObjectFoundException(Class<?> clazz, Object... params) {
        super("Entity: " + clazz.getSimpleName() + " couldn't save because is found using parameters: " + Arrays.stream(params)
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }
}
