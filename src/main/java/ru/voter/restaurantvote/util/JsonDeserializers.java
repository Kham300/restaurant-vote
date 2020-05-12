package ru.voter.restaurantvote.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.voter.restaurantvote.config.WebSecurityConfig;

import java.io.IOException;

public class JsonDeserializers {
    public static class PasswordDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            String rowPassword = node.asText();
            return WebSecurityConfig.DELEGATING_PASSWORD_ENCODER.encode(rowPassword);
        }
    }
}
