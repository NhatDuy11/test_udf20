package my.custom.udf;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
public class validJson {
    public static boolean validateJson(JsonNode json, JsonSchema schema) {
        try {
            schema.validate(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {

        String jsonFilePath = "D:\\oracle.json";
        String schemaFilePath = "\\\\wsl.localhost\\Ubuntu\\home\\nhatduy\\schema.json";
        ObjectMapper mapper = new ObjectMapper();


        JsonNode json = mapper.readTree(new File(jsonFilePath));


        JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
        JsonSchema schema = schemaFactory.getJsonSchema(mapper.readTree(new File(schemaFilePath)));

        boolean isValid = validateJson(json, schema);
        System.out.println("Is valid: " + isValid);
    }
}

