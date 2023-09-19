package my.custom.udf;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import java.io.IOException;
@UdfDescription(name = "xmlToJson", description = "Converts XML string to a JSON string1", author = "example user", version = "1.0.2")
    public class XmltoJsonUdf {
    @Udf(description = "Converts XML string to a JSON string")
    public String xmlToJson(@UdfParameter(value = "xml", description = "The XML string to convert") String xml) {
        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Object data = xmlMapper.readValue(xml, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert XML to JSON", e);
        }
    }


}
