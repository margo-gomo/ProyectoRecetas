package Modelo.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT);

    private JsonUtil() {}

    public static <T> void writeListToFile(List<T> list, File file) throws IOException {
        MAPPER.writeValue(file, list);
    }

    public static <T> List<T> readListFromFile(File file, TypeReference<List<T>> typeRef) throws IOException {
        return MAPPER.readValue(file, typeRef);
    }

    public static <T> void writeObjectToFile(T obj, File file) throws IOException {
        MAPPER.writeValue(file, obj);
    }
}
