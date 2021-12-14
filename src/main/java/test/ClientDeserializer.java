package test;

import test.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ClientDeserializer implements Deserializer<Client> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Client deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        Client user = null;
        try {
            user = mapper.readValue(bytes, Client.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void close() {

    }
}
