package com.example.demo.scraper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Utils {

    private final ObjectMapper mapper = new ObjectMapper();

    public String resolveUrlTemplate(String urlTemplate, List<String> paramValues) {
        int expectedCount = countPlaceholders(urlTemplate);
        if (expectedCount != paramValues.size()) {
            throw new IllegalArgumentException(
                    String.format("Template has %d placeholders, but %d values were provided",
                            expectedCount, paramValues.size())
            );
        }

        return String.format(urlTemplate, paramValues.toArray());
    }

    public String resolveBodyTemplate(Map<String, String> bodyTemplate) {
        try {
            return mapper.writeValueAsString(bodyTemplate);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("bodyTemplate is null");
        }
    }

    private int countPlaceholders(String template) {
        int count = 0;
        int index = 0;
        while ((index = template.indexOf("%S", index)) != -1) {
            count++;
            index += 2; // move past this %S
        }
        return count;
    }
}
