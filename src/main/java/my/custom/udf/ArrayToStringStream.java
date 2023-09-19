package my.custom.udf;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;
public class ArrayToStringStream {
    public static void main(final String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "array-to-string-stream");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group"); 
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        StreamsBuilder builder = new StreamsBuilder();
        ObjectMapper mapper = new ObjectMapper();

        KStream<String, String> sourceStream = builder.stream("xml_test2");
        KStream<String, String> transformedStream = sourceStream.mapValues(value -> {
            try{
                JsonNode jsonNode = mapper.readTree(value);
                ArrayNode arrayNode = (ArrayNode) jsonNode.get("c1");
                String[] array = new String[arrayNode.size()];
                for (int i = 0; i < arrayNode.size(); i++) {
                    array[i] = arrayNode.get(i).asText();
            }
                ((ObjectNode) jsonNode).put("c1", Arrays.stream(array).collect(Collectors.joining(" ")));
                return jsonNode.toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        transformedStream.to("xml_test3");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

}
