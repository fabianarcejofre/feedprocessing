package com.faj.feedprocessing.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.faj.feedprocessing.model.ProviderMessage;
import com.faj.feedprocessing.model.ProviderName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProviderServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProviderService providerService;

    @Test
    public void testProviderService() {
        List<String> jsonMessages = getJsonMessages();

        List<ProviderMessage> providerMessages = new ArrayList<>();
        List<Exception> serviceErrors = new ArrayList<>();

        for (String jsonMessage: jsonMessages) {
            try {
                providerMessages.add(providerService.processMessage(
                        inferProviderName(jsonMessage).getProviderName(),
                        objectMapper.readTree(jsonMessage)));
            } catch (Exception e) {
                serviceErrors.add(e);
            }
        }

        assertEquals(4, providerMessages.size());
        assertEquals(3, serviceErrors.size());
    }

    private ProviderName inferProviderName(String jsonMessage) {
        if (jsonMessage.contains("msg_type")) {
            return ProviderName.PROVIDER_ALPHA;
        }
        return ProviderName.PROVIDER_BETA;
    }

    private List<String> getJsonMessages() {
        List<String> jsonMessages = new ArrayList<>();
        jsonMessages.add("");
        jsonMessages.add("{\n" +
                "\"msg_type\": \"odds_update\",\n" +
                "\"event_id\": \"ev123\",\n" +
                "\"values\": {\n" +
                "\"1\": 2.0,\n" +
                "\"X\": 3.1,\n" +
                "\"2\": 3.8\n" +
                "}\n" +
                "}");
        jsonMessages.add("{\n" +
                "\"type\": \"SETTLEMENT\",\n" +
                "\"event_id\": \"ev456\",\n" +
                "\"result\": \"away\"\n" +
                "}");
        jsonMessages.add("{\n" +
                "\"type\": \"ODDS\",\n" +
                "\"event_id\": \"ev456\",\n" +
                "\"odds\": {\n" +
                "\"home\": 1.95,\n" +
                "\"draw\": 3.2,\n" +
                "\"away\": 4.0\n" +
                "}\n" +
                "}");
        jsonMessages.add("{\n" +
                "\"msg_type\": \"settlement\",\n" +
                "\"event_id\": \"ev123\",\n" +
                "\"outcome\": \"1\"\n" +
                "}");
        jsonMessages.add("{\n" +
                "\"type\": \"OTHER\",\n" +
                "\"event_id\": \"ev456\"\n" +
                "}");
        jsonMessages.add("{\n" +
                "\"msg_type\": \"unknown\",\n" +
                "\"event_id\": \"ev123\"" +
                "}");

        return jsonMessages;
    }

}
