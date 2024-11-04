package app.service;

import app.dtos.FetchDTO;
import app.enums.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiFetch {

    private static final String API_URL = "https://packingapi.cphbusinessapps.dk/packinglist/";
    private static ObjectMapper om = new ObjectMapper();
    private static FetchDTO fetchDTO;
    private static final Logger logger = LoggerFactory.getLogger(ApiFetch.class);

    public static void main(String[] args) {
//        System.out.println(get().toString());
    }

    public static FetchDTO get(String category) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + category.toLowerCase()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                om.registerModule(new JavaTimeModule());
                om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                fetchDTO = om.readValue(response.body(), FetchDTO.class);
            } else {
                System.out.println("API Fetch GET request failed. Status code: " + response.statusCode());
                logger.warn("API Fetch GET request failed. Status code: " + response.statusCode());
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return fetchDTO;
    }
}
