package my.custom.udf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class JsonSerializer<T> implements Deserializer<T> {
    private ObjectMapper objectMapper;
    private Class<T> tClass;
    public JsonSerializer(Class<T> tClass) {
        this.objectMapper = new ObjectMapper();
        this.tClass = tClass;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, tClass);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }
}
