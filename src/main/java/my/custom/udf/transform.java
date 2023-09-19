package my.custom.udf;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.fge.jsonschema.main.JsonSchema;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
public class transform {
    public static boolean validateJson(JsonNode jsNode, JsonSchema schema) {
        try {
            schema.validate(jsNode);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void main(String[] args) {

        File xmlFile = new File("D:\\\\oracle.XML");

        // chuyển đổi dữ liệu XML sang dạng đối tượng java
        XmlMapper xmlMapper = new XmlMapper();
        // sử dụng object mapper chuyển đối tượng java sang dạng JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Object data = xmlMapper.readValue(xmlFile, Object.class);
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
